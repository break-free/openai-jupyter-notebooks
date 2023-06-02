
package org.apache.fineract.infrastructure.reportmailingjob.data;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.infrastructure.dataqueries.data.ReportData;
public final class ReportMailingJobData {
    private final Long id;
    private final String name;
    private final String description;
    private final ZonedDateTime startDateTime;
    private final String recurrence;
    private final ReportMailingJobTimelineData timeline;
    private final String emailRecipients;
    private final String emailSubject;
    private final String emailMessage;
    private final EnumOptionData emailAttachmentFileFormat;
    private final ReportData stretchyReport;
    private final String stretchyReportParamMap;
    private final ZonedDateTime previousRunDateTime;
    private final ZonedDateTime nextRunDateTime;
    private final String previousRunStatus;
    private final String previousRunErrorLog;
    private final String previousRunErrorMessage;
    private final Integer numberOfRuns;
    private final boolean isActive;
    private final List<EnumOptionData> emailAttachmentFileFormatOptions;
    private final List<EnumOptionData> stretchyReportParamDateOptions;
    private final Long runAsUserId;
    private ReportMailingJobData(final Long id, final String name, final String description, final ZonedDateTime startDateTime,
            final String recurrence, final ReportMailingJobTimelineData timeline, final String emailRecipients, final String emailSubject,
            final String emailMessage, final EnumOptionData emailAttachmentFileFormat, final ReportData stretchyReport,
            final String stretchyReportParamMap, final ZonedDateTime previousRunDateTime, final ZonedDateTime nextRunDateTime,
            final String previousRunStatus, final String previousRunErrorLog, final String previousRunErrorMessage,
            final Integer numberOfRuns, final boolean isActive, final List<EnumOptionData> emailAttachmentFileFormatOptions,
            final List<EnumOptionData> stretchyReportParamDateOptions, final Long runAsUserId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.recurrence = recurrence;
        this.timeline = timeline;
        this.emailRecipients = emailRecipients;
        this.emailMessage = emailMessage;
        this.emailSubject = emailSubject;
        this.emailAttachmentFileFormat = emailAttachmentFileFormat;
        this.stretchyReport = stretchyReport;
        this.stretchyReportParamMap = stretchyReportParamMap;
        this.previousRunDateTime = previousRunDateTime;
        this.nextRunDateTime = nextRunDateTime;
        this.previousRunStatus = previousRunStatus;
        this.previousRunErrorLog = previousRunErrorLog;
        this.previousRunErrorMessage = previousRunErrorMessage;
        this.isActive = isActive;
        this.numberOfRuns = numberOfRuns;
        this.emailAttachmentFileFormatOptions = emailAttachmentFileFormatOptions;
        this.stretchyReportParamDateOptions = stretchyReportParamDateOptions;
        this.runAsUserId = runAsUserId;
    }
    public static ReportMailingJobData newInstance(final Long id, final String name, final String description,
            final ZonedDateTime startDateTime, final String recurrence, final ReportMailingJobTimelineData timeline,
            final String emailRecipients, final String emailSubject, final String emailMessage,
            final EnumOptionData emailAttachmentFileFormat, final ReportData stretchyReport, final String stretchyReportParamMap,
            final ZonedDateTime previousRunDateTime, final ZonedDateTime nextRunDateTime, final String previousRunStatus,
            final String previousRunErrorLog, final String previousRunErrorMessage, final Integer numberOfRuns, final boolean isActive,
            final Long runAsUserId) {
        return new ReportMailingJobData(id, name, description, startDateTime, recurrence, timeline, emailRecipients, emailSubject,
                emailMessage, emailAttachmentFileFormat, stretchyReport, stretchyReportParamMap, previousRunDateTime, nextRunDateTime,
                previousRunStatus, previousRunErrorLog, previousRunErrorMessage, numberOfRuns, isActive, null, null, runAsUserId);
    }
    public static ReportMailingJobData newInstance(final List<EnumOptionData> emailAttachmentFileFormatOptions,
            final List<EnumOptionData> stretchyReportParamDateOptions) {
        return new ReportMailingJobData(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, false, emailAttachmentFileFormatOptions, stretchyReportParamDateOptions, null);
    }
    public static ReportMailingJobData newInstance(final ReportMailingJobData dataWithoutEnumOptions,
            final ReportMailingJobData dataWithEnumOptions) {
        return new ReportMailingJobData(dataWithoutEnumOptions.id, dataWithoutEnumOptions.name, dataWithoutEnumOptions.description,
                dataWithoutEnumOptions.startDateTime, dataWithoutEnumOptions.recurrence, dataWithoutEnumOptions.timeline,
                dataWithoutEnumOptions.emailRecipients, dataWithoutEnumOptions.emailSubject, dataWithoutEnumOptions.emailMessage,
                dataWithoutEnumOptions.emailAttachmentFileFormat, dataWithoutEnumOptions.stretchyReport,
                dataWithoutEnumOptions.stretchyReportParamMap, dataWithoutEnumOptions.previousRunDateTime,
                dataWithoutEnumOptions.nextRunDateTime, dataWithoutEnumOptions.previousRunStatus,
                dataWithoutEnumOptions.previousRunErrorLog, dataWithoutEnumOptions.previousRunErrorMessage,
                dataWithoutEnumOptions.numberOfRuns, dataWithoutEnumOptions.isActive, dataWithEnumOptions.emailAttachmentFileFormatOptions,
                dataWithEnumOptions.stretchyReportParamDateOptions, dataWithoutEnumOptions.runAsUserId);
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }
    public String getRecurrence() {
        return recurrence;
    }
    public ReportMailingJobTimelineData getTimeline() {
        return timeline;
    }
    public String getEmailRecipients() {
        return emailRecipients;
    }
    public String getEmailSubject() {
        return emailSubject;
    }
    public String getEmailMessage() {
        return emailMessage;
    }
    public EnumOptionData getEmailAttachmentFileFormat() {
        return emailAttachmentFileFormat;
    }
    public ReportData getStretchyReport() {
        return stretchyReport;
    }
    public String getStretchyReportParamMap() {
        return stretchyReportParamMap;
    }
    public ZonedDateTime getPreviousRunDateTime() {
        return previousRunDateTime;
    }
    public ZonedDateTime getNextRunDateTime() {
        return nextRunDateTime;
    }
    public String getPreviousRunStatus() {
        return previousRunStatus;
    }
    public String getPreviousRunErrorLog() {
        return previousRunErrorLog;
    }
    public String getPreviousRunErrorMessage() {
        return previousRunErrorMessage;
    }
    public boolean isActive() {
        return isActive;
    }
    public List<EnumOptionData> getEmailAttachmentFileFormatOptions() {
        return emailAttachmentFileFormatOptions;
    }
    public List<EnumOptionData> getStretchyReportParamDateOptions() {
        return this.stretchyReportParamDateOptions;
    }
    public Integer getNumberOfRuns() {
        return numberOfRuns;
    }
    public Long getRunAsUserId() {
        return runAsUserId;
    }
}
