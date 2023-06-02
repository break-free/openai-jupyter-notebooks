
package org.apache.fineract.infrastructure.reportmailingjob.service;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.dataqueries.domain.Report;
import org.apache.fineract.infrastructure.dataqueries.domain.ReportRepositoryWrapper;
import org.apache.fineract.infrastructure.dataqueries.service.ReadReportingService;
import org.apache.fineract.infrastructure.documentmanagement.contentrepository.FileSystemContentRepository;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.infrastructure.report.provider.ReportingProcessServiceProvider;
import org.apache.fineract.infrastructure.report.service.ReportingProcessService;
import org.apache.fineract.infrastructure.reportmailingjob.ReportMailingJobConstants;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobEmailAttachmentFileFormat;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobEmailData;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobPreviousRunStatus;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobStretchyReportParamDateOption;
import org.apache.fineract.infrastructure.reportmailingjob.domain.ReportMailingJob;
import org.apache.fineract.infrastructure.reportmailingjob.domain.ReportMailingJobRepository;
import org.apache.fineract.infrastructure.reportmailingjob.domain.ReportMailingJobRepositoryWrapper;
import org.apache.fineract.infrastructure.reportmailingjob.domain.ReportMailingJobRunHistory;
import org.apache.fineract.infrastructure.reportmailingjob.domain.ReportMailingJobRunHistoryRepository;
import org.apache.fineract.infrastructure.reportmailingjob.util.ReportMailingJobDateUtil;
import org.apache.fineract.infrastructure.reportmailingjob.validation.ReportMailingJobValidator;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
import org.apache.fineract.useradministration.domain.AppUser;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ReportMailingJobWritePlatformServiceImpl implements ReportMailingJobWritePlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportMailingJobWritePlatformServiceImpl.class);
    private final ReportRepositoryWrapper reportRepositoryWrapper;
    private final ReportMailingJobValidator reportMailingJobValidator;
    private final ReportMailingJobRepositoryWrapper reportMailingJobRepositoryWrapper;
    private final ReportMailingJobRepository reportMailingJobRepository;
    private final PlatformSecurityContext platformSecurityContext;
    private final ReportMailingJobEmailService reportMailingJobEmailService;
    private final ReadReportingService readReportingService;
    private final ReportingProcessServiceProvider reportingProcessServiceProvider;
    private final ReportMailingJobRunHistoryRepository reportMailingJobRunHistoryRepository;
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    public ReportMailingJobWritePlatformServiceImpl(final ReportRepositoryWrapper reportRepositoryWrapper,
            final ReportMailingJobValidator reportMailingJobValidator,
            final ReportMailingJobRepositoryWrapper reportMailingJobRepositoryWrapper,
            final ReportMailingJobRepository reportMailingJobRepository, final PlatformSecurityContext platformSecurityContext,
            final ReportMailingJobEmailService reportMailingJobEmailService, final ReadReportingService readReportingService,
            final ReportMailingJobRunHistoryRepository reportMailingJobRunHistoryRepository,
            final ReportingProcessServiceProvider reportingProcessServiceProvider) {
        this.reportRepositoryWrapper = reportRepositoryWrapper;
        this.reportMailingJobValidator = reportMailingJobValidator;
        this.reportMailingJobRepositoryWrapper = reportMailingJobRepositoryWrapper;
        this.reportMailingJobRepository = reportMailingJobRepositoryWrapper.getReportMailingJobRepository();
        this.platformSecurityContext = platformSecurityContext;
        this.reportMailingJobEmailService = reportMailingJobEmailService;
        this.readReportingService = readReportingService;
        this.reportMailingJobRunHistoryRepository = reportMailingJobRunHistoryRepository;
        this.reportingProcessServiceProvider = reportingProcessServiceProvider;
    }
    @Override
    @Transactional
    public CommandProcessingResult createReportMailingJob(JsonCommand jsonCommand) {
        try {
            this.reportMailingJobValidator.validateCreateRequest(jsonCommand);
            final AppUser appUser = this.platformSecurityContext.authenticatedUser();
            final Report stretchyReport = this.reportRepositoryWrapper.findOneThrowExceptionIfNotFound(
                    jsonCommand.longValueOfParameterNamed(ReportMailingJobConstants.STRETCHY_REPORT_ID_PARAM_NAME));
            final ReportMailingJob reportMailingJob = ReportMailingJob.newInstance(jsonCommand, stretchyReport, appUser);
            this.reportMailingJobRepository.saveAndFlush(reportMailingJob);
            return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(reportMailingJob.getId())
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(jsonCommand, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    @Override
    @Transactional
    public CommandProcessingResult updateReportMailingJob(Long reportMailingJobId, JsonCommand jsonCommand) {
        try {
            this.reportMailingJobValidator.validateUpdateRequest(jsonCommand);
            final ReportMailingJob reportMailingJob = this.reportMailingJobRepositoryWrapper
                    .findOneThrowExceptionIfNotFound(reportMailingJobId);
            final Map<String, Object> changes = reportMailingJob.update(jsonCommand);
            final String recurrence = reportMailingJob.getRecurrence();
            LocalDateTime nextRunDateTime = reportMailingJob.getNextRunDateTime();
            if (changes.containsKey(ReportMailingJobConstants.STRETCHY_REPORT_ID_PARAM_NAME)) {
                final Long stretchyReportId = (Long) changes.get(ReportMailingJobConstants.STRETCHY_REPORT_ID_PARAM_NAME);
                final Report stretchyReport = this.reportRepositoryWrapper.findOneThrowExceptionIfNotFound(stretchyReportId);
                reportMailingJob.update(stretchyReport);
            }
            if (changes.containsKey(ReportMailingJobConstants.RECURRENCE_PARAM_NAME)) {
                if (StringUtils.isNotBlank(recurrence)) {
                    LocalDateTime startDateTime = DateUtils.getLocalDateTimeOfTenant();
                    if (changes.containsKey(ReportMailingJobConstants.START_DATE_TIME_PARAM_NAME)) {
                        startDateTime = reportMailingJob.getStartDateTime();
                    }
                    startDateTime = reportMailingJob.getStartDateTime();
                    final LocalDateTime nextRecurringDateTime = this.createNextRecurringDateTime(recurrence, startDateTime);
                    reportMailingJob.updateNextRunDateTime(nextRecurringDateTime);
                } else if (StringUtils.isBlank(recurrence) && (nextRunDateTime != null)) {
                    reportMailingJob.updateNextRunDateTime(null);
                }
            }
            if (changes.containsKey(ReportMailingJobConstants.START_DATE_TIME_PARAM_NAME)) {
                final LocalDateTime startDateTime = reportMailingJob.getStartDateTime();
                LocalDateTime nextRecurringDateTime = startDateTime;
                if (StringUtils.isNotBlank(recurrence)) {
                    nextRecurringDateTime = this.createNextRecurringDateTime(recurrence, startDateTime);
                }
                reportMailingJob.updateNextRunDateTime(nextRecurringDateTime);
            }
            if (!changes.isEmpty()) {
                this.reportMailingJobRepository.saveAndFlush(reportMailingJob);
            }
            return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(reportMailingJob.getId())
                    .with(changes).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(jsonCommand, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    @Override
    @Transactional
    public CommandProcessingResult deleteReportMailingJob(Long reportMailingJobId) {
        final ReportMailingJob reportMailingJob = this.reportMailingJobRepositoryWrapper
                .findOneThrowExceptionIfNotFound(reportMailingJobId);
        reportMailingJob.delete();
        this.reportMailingJobRepository.save(reportMailingJob);
        return new CommandProcessingResultBuilder().withEntityId(reportMailingJobId).build();
    }
    @Override
    @CronTarget(jobName = JobName.EXECUTE_REPORT_MAILING_JOBS)
    public void executeReportMailingJobs() throws JobExecutionException {
        final Collection<ReportMailingJob> reportMailingJobCollection = this.reportMailingJobRepository
                .findByIsActiveTrueAndIsDeletedFalse();
        for (ReportMailingJob reportMailingJob : reportMailingJobCollection) {
            final LocalDateTime localDateTimeOftenant = DateUtils.getLocalDateTimeOfTenant();
            final LocalDateTime nextRunDateTime = reportMailingJob.getNextRunDateTime();
            if (nextRunDateTime != null && nextRunDateTime.isBefore(localDateTimeOftenant)) {
                final ReportMailingJobEmailAttachmentFileFormat emailAttachmentFileFormat = ReportMailingJobEmailAttachmentFileFormat
                        .newInstance(reportMailingJob.getEmailAttachmentFileFormat());
                if (emailAttachmentFileFormat != null && emailAttachmentFileFormat.isValid()) {
                    final Report stretchyReport = reportMailingJob.getStretchyReport();
                    final String reportName = (stretchyReport != null) ? stretchyReport.getReportName() : null;
                    final StringBuilder errorLog = new StringBuilder();
                    final Map<String, String> validateStretchyReportParamMap = this.reportMailingJobValidator
                            .validateStretchyReportParamMap(reportMailingJob.getStretchyReportParamMap());
                    MultivaluedMap<String, String> reportParams = new MultivaluedStringMap();
                    if (validateStretchyReportParamMap != null) {
                        Iterator<Map.Entry<String, String>> validateStretchyReportParamMapEntries = validateStretchyReportParamMap
                                .entrySet().iterator();
                        while (validateStretchyReportParamMapEntries.hasNext()) {
                            Map.Entry<String, String> validateStretchyReportParamMapEntry = validateStretchyReportParamMapEntries.next();
                            String key = validateStretchyReportParamMapEntry.getKey();
                            String value = validateStretchyReportParamMapEntry.getValue();
                            if (StringUtils.containsIgnoreCase(key, "date")) {
                                ReportMailingJobStretchyReportParamDateOption reportMailingJobStretchyReportParamDateOption = ReportMailingJobStretchyReportParamDateOption
                                        .newInstance(value);
                                if (reportMailingJobStretchyReportParamDateOption.isValid()) {
                                    value = ReportMailingJobDateUtil.getDateAsString(reportMailingJobStretchyReportParamDateOption);
                                }
                            }
                            reportParams.add(key, value);
                        }
                    }
                    this.generateReportOutputStream(reportMailingJob, emailAttachmentFileFormat, reportParams, reportName, errorLog);
                    this.updateReportMailingJobAfterJobExecution(reportMailingJob, errorLog, localDateTimeOftenant);
                }
            }
        }
    }
    private void updateReportMailingJobAfterJobExecution(final ReportMailingJob reportMailingJob, final StringBuilder errorLog,
            final LocalDateTime jobStartDateTime) {
        final String recurrence = reportMailingJob.getRecurrence();
        final LocalDateTime nextRunDateTime = reportMailingJob.getNextRunDateTime();
        ReportMailingJobPreviousRunStatus reportMailingJobPreviousRunStatus = ReportMailingJobPreviousRunStatus.SUCCESS;
        reportMailingJob.updatePreviousRunErrorLog(null);
        if (errorLog != null && errorLog.length() > 0) {
            reportMailingJobPreviousRunStatus = ReportMailingJobPreviousRunStatus.ERROR;
            reportMailingJob.updatePreviousRunErrorLog(errorLog.toString());
        }
        reportMailingJob.increaseNumberOfRunsByOne();
        reportMailingJob.updatePreviousRunStatus(reportMailingJobPreviousRunStatus.getValue());
        reportMailingJob.updatePreviousRunDateTime(reportMailingJob.getNextRunDateTime());
        if (StringUtils.isEmpty(recurrence)) {
            reportMailingJob.deactivate();
            reportMailingJob.updateNextRunDateTime(null);
        } else if (nextRunDateTime != null) {
            final LocalDateTime nextRecurringDateTime = this.createNextRecurringDateTime(recurrence, nextRunDateTime);
            reportMailingJob.updateNextRunDateTime(nextRecurringDateTime);
        }
        this.reportMailingJobRepository.save(reportMailingJob);
        this.createReportMailingJobRunHistroryAfterJobExecution(reportMailingJob, errorLog, jobStartDateTime,
                reportMailingJobPreviousRunStatus.getValue());
    }
    private LocalDateTime createNextRecurringDateTime(final String recurrencePattern, final LocalDateTime startDateTime) {
        LocalDateTime nextRecurringDateTime = null;
        if (StringUtils.isNotBlank(recurrencePattern) && startDateTime != null) {
            final LocalDateTime nextDayLocalDate = startDateTime.plus(Duration.ofDays(1));
            nextRecurringDateTime = CalendarUtils.getNextRecurringDate(recurrencePattern, startDateTime, nextDayLocalDate);
        }
        return nextRecurringDateTime;
    }
    private void createReportMailingJobRunHistroryAfterJobExecution(final ReportMailingJob reportMailingJob, final StringBuilder errorLog,
            final LocalDateTime jobStartDateTime, final String jobRunStatus) {
        final LocalDateTime jobEndDateTime = DateUtils.getLocalDateTimeOfTenant();
        final String errorLogToString = (errorLog != null) ? errorLog.toString() : null;
        final ReportMailingJobRunHistory reportMailingJobRunHistory = ReportMailingJobRunHistory.newInstance(reportMailingJob,
                jobStartDateTime, jobEndDateTime, jobRunStatus, null, errorLogToString);
        this.reportMailingJobRunHistoryRepository.save(reportMailingJobRunHistory);
    }
    private void handleDataIntegrityIssues(final JsonCommand jsonCommand, final Throwable realCause,
            final NonTransientDataAccessException dve) {
        if (realCause.getMessage().contains(ReportMailingJobConstants.NAME_PARAM_NAME)) {
            final String name = jsonCommand.stringValueOfParameterNamed(ReportMailingJobConstants.NAME_PARAM_NAME);
            throw new PlatformDataIntegrityException("error.msg.report.mailing.job.duplicate.name",
                    "Report mailing job with name `" + name + "` already exists", ReportMailingJobConstants.NAME_PARAM_NAME, name);
        }
        LOG.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.charge.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource: " + realCause.getMessage());
    }
    private StringBuilder generateReportOutputStream(final ReportMailingJob reportMailingJob,
            final ReportMailingJobEmailAttachmentFileFormat emailAttachmentFileFormat, final MultivaluedMap<String, String> reportParams,
            final String reportName, final StringBuilder errorLog) {
        try {
            final boolean isSelfServiceUserReport = false;
            final String reportType = this.readReportingService.getReportType(reportName, isSelfServiceUserReport, false);
            final ReportingProcessService reportingProcessService = this.reportingProcessServiceProvider
                    .findReportingProcessService(reportType);
            if (reportingProcessService != null) {
                final Response processReport = reportingProcessService.processRequest(reportName, reportParams);
                final Object reponseObject = (processReport != null) ? processReport.getEntity() : null;
                if (reponseObject != null && reponseObject.getClass().equals(ByteArrayOutputStream.class)) {
                    final ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream.class.cast(reponseObject);
                    final String fileLocation = FileSystemContentRepository.FINERACT_BASE_DIR + File.separator + "";
                    final String fileNameWithoutExtension = fileLocation + File.separator + reportName;
                    if (!new File(fileLocation).isDirectory()) {
                        new File(fileLocation).mkdirs();
                    }
                    if ((byteArrayOutputStream == null) || byteArrayOutputStream.size() == 0) {
                        errorLog.append("Report processing failed, empty output stream created");
                    } else if ((errorLog != null && errorLog.length() == 0) && (byteArrayOutputStream.size() > 0)) {
                        final String fileName = fileNameWithoutExtension + "." + emailAttachmentFileFormat.getValue();
                        this.sendReportFileToEmailRecipients(reportMailingJob, fileName, byteArrayOutputStream, errorLog);
                    }
                } else {
                    errorLog.append("Response object entity is not equal to ByteArrayOutputStream ---------- ");
                }
            } else {
                errorLog.append(ReportingProcessServiceProvider.SERVICE_MISSING + reportType);
            }
        } catch (Exception e) {
            errorLog.append("The ReportMailingJobWritePlatformServiceImpl.generateReportOutputStream method threw an Exception: " + e
                    + " ---------- ");
        }
        return errorLog;
    }
    private void sendReportFileToEmailRecipients(final ReportMailingJob reportMailingJob, final String fileName,
            final ByteArrayOutputStream byteArrayOutputStream, final StringBuilder errorLog) {
        final Set<String> emailRecipients = this.reportMailingJobValidator.validateEmailRecipients(reportMailingJob.getEmailRecipients());
        try {
            final File file = new File(fileName);
            final FileOutputStream outputStream = new FileOutputStream(file);
            byteArrayOutputStream.writeTo(outputStream);
            for (String emailRecipient : emailRecipients) {
                final ReportMailingJobEmailData reportMailingJobEmailData = new ReportMailingJobEmailData(emailRecipient,
                        reportMailingJob.getEmailMessage(), reportMailingJob.getEmailSubject(), file);
                this.reportMailingJobEmailService.sendEmailWithAttachment(reportMailingJobEmailData);
            }
            outputStream.close();
        } catch (IOException e) {
            errorLog.append("The ReportMailingJobWritePlatformServiceImpl.sendReportFileToEmailRecipients method threw an IOException "
                    + "exception: " + e + " ---------- ");
        }
    }
}
