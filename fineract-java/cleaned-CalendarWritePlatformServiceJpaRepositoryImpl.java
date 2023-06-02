
package org.apache.fineract.portfolio.calendar.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.portfolio.calendar.CalendarConstants.CalendarSupportedParameters;
import org.apache.fineract.portfolio.calendar.domain.Calendar;
import org.apache.fineract.portfolio.calendar.domain.CalendarEntityType;
import org.apache.fineract.portfolio.calendar.domain.CalendarHistory;
import org.apache.fineract.portfolio.calendar.domain.CalendarHistoryRepository;
import org.apache.fineract.portfolio.calendar.domain.CalendarInstance;
import org.apache.fineract.portfolio.calendar.domain.CalendarInstanceRepository;
import org.apache.fineract.portfolio.calendar.domain.CalendarRepository;
import org.apache.fineract.portfolio.calendar.domain.CalendarType;
import org.apache.fineract.portfolio.calendar.exception.CalendarNotFoundException;
import org.apache.fineract.portfolio.calendar.serialization.CalendarCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.group.domain.GroupRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
@Service
public class CalendarWritePlatformServiceJpaRepositoryImpl implements CalendarWritePlatformService {
    private final CalendarRepository calendarRepository;
    private final CalendarHistoryRepository calendarHistoryRepository;
    private final CalendarCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    private final CalendarInstanceRepository calendarInstanceRepository;
    private final LoanWritePlatformService loanWritePlatformService;
    private final ConfigurationDomainService configurationDomainService;
    private final GroupRepositoryWrapper groupRepository;
    private final LoanRepositoryWrapper loanRepositoryWrapper;
    private final ClientRepositoryWrapper clientRepository;
    @Autowired
    public CalendarWritePlatformServiceJpaRepositoryImpl(final CalendarRepository calendarRepository,
            final CalendarHistoryRepository calendarHistoryRepository, final CalendarCommandFromApiJsonDeserializer fromApiJsonDeserializer,
            final CalendarInstanceRepository calendarInstanceRepository, final LoanWritePlatformService loanWritePlatformService,
            final ConfigurationDomainService configurationDomainService, final GroupRepositoryWrapper groupRepository,
            final LoanRepositoryWrapper loanRepositoryWrapper, final ClientRepositoryWrapper clientRepository) {
        this.calendarRepository = calendarRepository;
        this.calendarHistoryRepository = calendarHistoryRepository;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
        this.calendarInstanceRepository = calendarInstanceRepository;
        this.loanWritePlatformService = loanWritePlatformService;
        this.configurationDomainService = configurationDomainService;
        this.groupRepository = groupRepository;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
        this.clientRepository = clientRepository;
    }
    @Override
    public CommandProcessingResult createCalendar(final JsonCommand command) {
        this.fromApiJsonDeserializer.validateForCreate(command.json());
        Long entityId = null;
        CalendarEntityType entityType = CalendarEntityType.INVALID;
        LocalDate entityActivationDate = null;
        Group centerOrGroup = null;
        if (command.getGroupId() != null) {
            centerOrGroup = this.groupRepository.findOneWithNotFoundDetection(command.getGroupId());
            entityActivationDate = centerOrGroup.getActivationLocalDate();
            entityType = centerOrGroup.isCenter() ? CalendarEntityType.CENTERS : CalendarEntityType.GROUPS;
            entityId = command.getGroupId();
        } else if (command.getLoanId() != null) {
            final Loan loan = this.loanRepositoryWrapper.findOneWithNotFoundDetection(command.getLoanId(), true);
            entityActivationDate = (loan.getApprovedOnDate() == null) ? loan.getSubmittedOnDate() : loan.getApprovedOnDate();
            entityType = CalendarEntityType.LOANS;
            entityId = command.getLoanId();
        } else if (command.getClientId() != null) {
            final Client client = this.clientRepository.findOneWithNotFoundDetection(command.getClientId());
            entityActivationDate = client.getActivationLocalDate();
            entityType = CalendarEntityType.CLIENTS;
            entityId = command.getClientId();
        }
        final Integer entityTypeId = entityType.getValue();
        final Calendar newCalendar = Calendar.fromJson(command);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("calendar");
        if (entityActivationDate == null || newCalendar.getStartDateLocalDate().isBefore(entityActivationDate)) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(command.dateFormat()).withLocale(command.extractLocale());
            String dateAsString = "";
            if (entityActivationDate != null) {
                dateAsString = formatter.format(entityActivationDate);
            }
            final String errorMessage = "cannot.be.before." + entityType.name().toLowerCase() + ".activation.date";
            baseDataValidator.reset().parameter(CalendarSupportedParameters.START_DATE.getValue()).value(dateAsString)
                    .failWithCodeNoParameterAddedToErrorCode(errorMessage);
        }
        if (centerOrGroup != null) {
            Long centerOrGroupId = centerOrGroup.getId();
            Integer centerOrGroupEntityTypeId = entityType.getValue();
            final Group parent = centerOrGroup.getParent();
            if (parent != null) {
                centerOrGroupId = parent.getId();
                centerOrGroupEntityTypeId = CalendarEntityType.CENTERS.getValue();
            }
            final CalendarInstance collectionCalendarInstance = this.calendarInstanceRepository
                    .findByEntityIdAndEntityTypeIdAndCalendarTypeId(centerOrGroupId, centerOrGroupEntityTypeId,
                            CalendarType.COLLECTION.getValue());
            if (collectionCalendarInstance != null) {
                final String errorMessage = "multiple.collection.calendar.not.supported";
                baseDataValidator.reset().failWithCodeNoParameterAddedToErrorCode(errorMessage);
            }
        }
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
        this.calendarRepository.saveAndFlush(newCalendar);
        final CalendarInstance newCalendarInstance = CalendarInstance.from(newCalendar, entityId, entityTypeId);
        this.calendarInstanceRepository.save(newCalendarInstance);
        return new CommandProcessingResultBuilder() 
                .withCommandId(command.commandId()) 
                .withEntityId(newCalendar.getId()) 
                .withClientId(command.getClientId()) 
                .withGroupId(command.getGroupId()) 
                .withLoanId(command.getLoanId()) 
                .build();
    }
    public void validateIsEditMeetingAllowed(Long groupId) {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("calendar");
        Group centerOrGroup = null;
        if (groupId != null) {
            centerOrGroup = this.groupRepository.findOneWithNotFoundDetection(groupId);
            final Group parent = centerOrGroup.getParent();
            if (centerOrGroup.isGroup() && parent != null) {
                Integer centerEntityTypeId = CalendarEntityType.CENTERS.getValue();
                final CalendarInstance collectionCalendarInstance = this.calendarInstanceRepository
                        .findByEntityIdAndEntityTypeIdAndCalendarTypeId(parent.getId(), centerEntityTypeId,
                                CalendarType.COLLECTION.getValue());
                if (collectionCalendarInstance != null) {
                    final String errorMessage = "meeting.created.at.center.cannot.be.edited.at.group.level";
                    baseDataValidator.reset().failWithCodeNoParameterAddedToErrorCode(errorMessage);
                }
            }
        }
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    @Override
    public CommandProcessingResult updateCalendar(final JsonCommand command) {
        this.validateIsEditMeetingAllowed(command.getGroupId());
        this.fromApiJsonDeserializer.validateForUpdate(command.json());
        Boolean areActiveEntitiesSynced = false;
        final Long calendarId = command.entityId();
        final Collection<Integer> loanStatuses = new ArrayList<>(Arrays.asList(LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getValue(),
                LoanStatus.APPROVED.getValue(), LoanStatus.ACTIVE.getValue()));
        final Integer numberOfActiveLoansSyncedWithThisCalendar = this.calendarInstanceRepository.countOfLoansSyncedWithCalendar(calendarId,
                loanStatuses);
        if (numberOfActiveLoansSyncedWithThisCalendar > 0) {
            areActiveEntitiesSynced = true;
        }
        final Calendar calendarForUpdate = this.calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFoundException(calendarId));
        final LocalDate oldStartDate = calendarForUpdate.getStartDate();
        final CalendarHistory calendarHistory = new CalendarHistory(calendarForUpdate, oldStartDate);
        Map<String, Object> changes = null;
        final Boolean reschedulebasedOnMeetingDates = command
                .booleanObjectValueOfParameterNamed(CalendarSupportedParameters.RESCHEDULE_BASED_ON_MEETING_DATES.getValue());
        LocalDate newMeetingDate = null;
        LocalDate presentMeetingDate = null;
        if (reschedulebasedOnMeetingDates != null && reschedulebasedOnMeetingDates) {
            newMeetingDate = command.localDateValueOfParameterNamed(CalendarSupportedParameters.NEW_MEETING_DATE.getValue());
            presentMeetingDate = command.localDateValueOfParameterNamed(CalendarSupportedParameters.PRESENT_MEETING_DATE.getValue());
            changes = calendarForUpdate.updateStartDateAndDerivedFeilds(newMeetingDate);
        } else {
            changes = calendarForUpdate.update(command, areActiveEntitiesSynced);
        }
        if (!changes.isEmpty()) {
            if (reschedulebasedOnMeetingDates == null) {
                presentMeetingDate = command.localDateValueOfParameterNamed(CalendarSupportedParameters.START_DATE.getValue());
            }
            if (null != newMeetingDate) {
                final LocalDate endDate = presentMeetingDate.minusDays(1);
                calendarHistory.updateEndDate(endDate);
            }
            this.calendarHistoryRepository.save(calendarHistory);
            Set<CalendarHistory> history = calendarForUpdate.getCalendarHistory();
            history.add(calendarHistory);
            calendarForUpdate.updateCalendarHistory(history);
            this.calendarRepository.saveAndFlush(calendarForUpdate);
            if (this.configurationDomainService.isRescheduleFutureRepaymentsEnabled() && calendarForUpdate.isRepeating()) {
                final Collection<CalendarInstance> loanCalendarInstances = this.calendarInstanceRepository
                        .findByCalendarIdAndEntityTypeId(calendarId, CalendarEntityType.LOANS.getValue());
                if (!CollectionUtils.isEmpty(loanCalendarInstances)) {
                    this.loanWritePlatformService.applyMeetingDateChanges(calendarForUpdate, loanCalendarInstances,
                            reschedulebasedOnMeetingDates, presentMeetingDate, newMeetingDate);
                }
            }
        }
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(calendarForUpdate.getId()).with(changes)
                .build();
    }
    @Override
    public CommandProcessingResult deleteCalendar(final Long calendarId) {
        final Calendar calendarForDelete = this.calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFoundException(calendarId));
        this.calendarRepository.delete(calendarForDelete);
        return new CommandProcessingResultBuilder().withCommandId(null).withEntityId(calendarId).build();
    }
    @Override
    public CommandProcessingResult createCalendarInstance(final Long calendarId, final Long entityId, final Integer entityTypeId) {
        final Calendar calendarForUpdate = this.calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFoundException(calendarId));
        final CalendarInstance newCalendarInstance = new CalendarInstance(calendarForUpdate, entityId, entityTypeId);
        this.calendarInstanceRepository.save(newCalendarInstance);
        return new CommandProcessingResultBuilder().withCommandId(null).withEntityId(calendarForUpdate.getId()).build();
    }
    @Override
    public CommandProcessingResult updateCalendarInstance(final Long calendarId, final Long entityId, final Integer entityTypeId) {
        final Calendar calendarForUpdate = this.calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarNotFoundException(calendarId));
        final CalendarInstance calendarInstanceForUpdate = this.calendarInstanceRepository
                .findByCalendarIdAndEntityIdAndEntityTypeId(calendarId, entityId, entityTypeId);
        this.calendarInstanceRepository.saveAndFlush(calendarInstanceForUpdate);
        return new CommandProcessingResultBuilder().withCommandId(null).withEntityId(calendarForUpdate.getId()).build();
    }
}
