
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.accounting.journalentry.service.JournalEntryWritePlatformService;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.monetary.domain.ApplicationCurrency;
import org.apache.fineract.organisation.monetary.domain.ApplicationCurrencyRepositoryWrapper;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.MoneyHelper;
import org.apache.fineract.portfolio.account.service.AccountTransfersWritePlatformService;
import org.apache.fineract.portfolio.loanaccount.data.LoanTermVariationsData;
import org.apache.fineract.portfolio.loanaccount.data.ScheduleGeneratorDTO;
import org.apache.fineract.portfolio.loanaccount.domain.ChangedTransactionDetail;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanAccountDomainService;
import org.apache.fineract.portfolio.loanaccount.domain.LoanLifecycleStateMachine;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallmentRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleTransactionProcessorFactory;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRescheduleRequestToTermVariationMapping;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
import org.apache.fineract.portfolio.loanaccount.domain.LoanSummaryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTermVariationType;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTermVariations;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionRepository;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.LoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleDTO;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.DefaultScheduledDateGenerator;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanApplicationTerms;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanRepaymentScheduleHistory;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanRepaymentScheduleHistoryRepository;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanScheduleGenerator;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanScheduleGeneratorFactory;
import org.apache.fineract.portfolio.loanaccount.loanschedule.service.LoanScheduleHistoryWritePlatformService;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.RescheduleLoansApiConstants;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.data.LoanRescheduleRequestDataValidator;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain.LoanRescheduleRequest;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain.LoanRescheduleRequestRepository;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.exception.LoanRescheduleRequestNotFoundException;
import org.apache.fineract.portfolio.loanaccount.service.LoanAssembler;
import org.apache.fineract.portfolio.loanaccount.service.LoanUtilService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class LoanRescheduleRequestWritePlatformServiceImpl implements LoanRescheduleRequestWritePlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(LoanRescheduleRequestWritePlatformServiceImpl.class);
    private final CodeValueRepositoryWrapper codeValueRepositoryWrapper;
    private final PlatformSecurityContext platformSecurityContext;
    private final LoanRescheduleRequestDataValidator loanRescheduleRequestDataValidator;
    private final LoanRescheduleRequestRepository loanRescheduleRequestRepository;
    private final ApplicationCurrencyRepositoryWrapper applicationCurrencyRepository;
    private final LoanRepaymentScheduleHistoryRepository loanRepaymentScheduleHistoryRepository;
    private final LoanScheduleHistoryWritePlatformService loanScheduleHistoryWritePlatformService;
    private final LoanTransactionRepository loanTransactionRepository;
    private final JournalEntryWritePlatformService journalEntryWritePlatformService;
    private final LoanRepositoryWrapper loanRepositoryWrapper;
    private final LoanAssembler loanAssembler;
    private final LoanUtilService loanUtilService;
    private final LoanRepaymentScheduleTransactionProcessorFactory loanRepaymentScheduleTransactionProcessorFactory;
    private final LoanScheduleGeneratorFactory loanScheduleFactory;
    private final LoanSummaryWrapper loanSummaryWrapper;
    private final AccountTransfersWritePlatformService accountTransfersWritePlatformService;
    private final DefaultScheduledDateGenerator scheduledDateGenerator = new DefaultScheduledDateGenerator();
    private final LoanAccountDomainService loanAccountDomainService;
    private final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository;
    @Autowired
    public LoanRescheduleRequestWritePlatformServiceImpl(final CodeValueRepositoryWrapper codeValueRepositoryWrapper,
            final PlatformSecurityContext platformSecurityContext,
            final LoanRescheduleRequestDataValidator loanRescheduleRequestDataValidator,
            final LoanRescheduleRequestRepository loanRescheduleRequestRepository,
            final ApplicationCurrencyRepositoryWrapper applicationCurrencyRepository,
            final LoanRepaymentScheduleHistoryRepository loanRepaymentScheduleHistoryRepository,
            final LoanScheduleHistoryWritePlatformService loanScheduleHistoryWritePlatformService,
            final LoanTransactionRepository loanTransactionRepository,
            final JournalEntryWritePlatformService journalEntryWritePlatformService, final LoanRepositoryWrapper loanRepositoryWrapper,
            final LoanAssembler loanAssembler, final LoanUtilService loanUtilService,
            final LoanRepaymentScheduleTransactionProcessorFactory loanRepaymentScheduleTransactionProcessorFactory,
            final LoanScheduleGeneratorFactory loanScheduleFactory, final LoanSummaryWrapper loanSummaryWrapper,
            final AccountTransfersWritePlatformService accountTransfersWritePlatformService,
            final LoanAccountDomainService loanAccountDomainService,
            final LoanRepaymentScheduleInstallmentRepository repaymentScheduleInstallmentRepository) {
        this.codeValueRepositoryWrapper = codeValueRepositoryWrapper;
        this.platformSecurityContext = platformSecurityContext;
        this.loanRescheduleRequestDataValidator = loanRescheduleRequestDataValidator;
        this.loanRescheduleRequestRepository = loanRescheduleRequestRepository;
        this.applicationCurrencyRepository = applicationCurrencyRepository;
        this.loanRepaymentScheduleHistoryRepository = loanRepaymentScheduleHistoryRepository;
        this.loanScheduleHistoryWritePlatformService = loanScheduleHistoryWritePlatformService;
        this.loanTransactionRepository = loanTransactionRepository;
        this.journalEntryWritePlatformService = journalEntryWritePlatformService;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
        this.loanAssembler = loanAssembler;
        this.loanUtilService = loanUtilService;
        this.loanRepaymentScheduleTransactionProcessorFactory = loanRepaymentScheduleTransactionProcessorFactory;
        this.loanScheduleFactory = loanScheduleFactory;
        this.loanSummaryWrapper = loanSummaryWrapper;
        this.accountTransfersWritePlatformService = accountTransfersWritePlatformService;
        this.loanAccountDomainService = loanAccountDomainService;
        this.repaymentScheduleInstallmentRepository = repaymentScheduleInstallmentRepository;
    }
    @Override
    @Transactional
    public CommandProcessingResult create(JsonCommand jsonCommand) {
        try {
            final Long loanId = jsonCommand.longValueOfParameterNamed(RescheduleLoansApiConstants.loanIdParamName);
            final Loan loan = this.loanAssembler.assembleFrom(loanId);
            this.loanRescheduleRequestDataValidator.validateForCreateAction(jsonCommand, loan);
            final Long rescheduleReasonId = jsonCommand.longValueOfParameterNamed(RescheduleLoansApiConstants.rescheduleReasonIdParamName);
            final CodeValue rescheduleReasonCodeValue = this.codeValueRepositoryWrapper.findOneWithNotFoundDetection(rescheduleReasonId);
            final Integer graceOnPrincipal = jsonCommand
                    .integerValueOfParameterNamed(RescheduleLoansApiConstants.graceOnPrincipalParamName);
            final Integer graceOnInterest = jsonCommand.integerValueOfParameterNamed(RescheduleLoansApiConstants.graceOnInterestParamName);
            final Integer extraTerms = jsonCommand.integerValueOfParameterNamed(RescheduleLoansApiConstants.extraTermsParamName);
            final BigDecimal interestRate = jsonCommand
                    .bigDecimalValueOfParameterNamed(RescheduleLoansApiConstants.newInterestRateParamName);
            final String rescheduleReasonComment = jsonCommand
                    .stringValueOfParameterNamed(RescheduleLoansApiConstants.rescheduleReasonCommentParamName);
            final Boolean recalculateInterest = jsonCommand
                    .booleanObjectValueOfParameterNamed(RescheduleLoansApiConstants.recalculateInterestParamName);
            final LocalDate endDate = jsonCommand.localDateValueOfParameterNamed(RescheduleLoansApiConstants.endDateParamName);
            final BigDecimal emi = jsonCommand.bigDecimalValueOfParameterNamed(RescheduleLoansApiConstants.emiParamName);
            LocalDate submittedOnDate = null;
            if (jsonCommand.hasParameter(RescheduleLoansApiConstants.submittedOnDateParamName)) {
                submittedOnDate = jsonCommand.localDateValueOfParameterNamed(RescheduleLoansApiConstants.submittedOnDateParamName);
            }
            LocalDate rescheduleFromDate = null;
            Integer rescheduleFromInstallment = null;
            LocalDate adjustedDueDate = null;
            if (jsonCommand.hasParameter(RescheduleLoansApiConstants.rescheduleFromDateParamName)) {
                LocalDate localDate = jsonCommand.localDateValueOfParameterNamed(RescheduleLoansApiConstants.rescheduleFromDateParamName);
                if (localDate != null) {
                    LoanRepaymentScheduleInstallment installment = loan.getRepaymentScheduleInstallment(localDate);
                    rescheduleFromInstallment = installment.getInstallmentNumber();
                    rescheduleFromDate = localDate;
                }
            }
            if (jsonCommand.hasParameter(RescheduleLoansApiConstants.adjustedDueDateParamName)) {
                adjustedDueDate = jsonCommand.localDateValueOfParameterNamed(RescheduleLoansApiConstants.adjustedDueDateParamName);
            }
            final LoanRescheduleRequest loanRescheduleRequest = LoanRescheduleRequest.instance(loan,
                    LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getValue(), rescheduleFromInstallment, rescheduleFromDate,
                    recalculateInterest, rescheduleReasonCodeValue, rescheduleReasonComment, submittedOnDate,
                    this.platformSecurityContext.authenticatedUser(), null, null, null, null);
            List<LoanRescheduleRequestToTermVariationMapping> loanRescheduleRequestToTermVariationMappings = new ArrayList<>();
            final Boolean isActive = false;
            final boolean isSpecificToInstallment = false;
            BigDecimal decimalValue = null;
            LocalDate dueDate = null;
            createLoanTermVariationsForRegularLoans(loan, graceOnPrincipal, graceOnInterest, extraTerms, interestRate, rescheduleFromDate,
                    adjustedDueDate, loanRescheduleRequest, loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment,
                    decimalValue, dueDate, endDate, emi);
            this.loanRescheduleRequestRepository.saveAndFlush(loanRescheduleRequest);
            return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(loanRescheduleRequest.getId())
                    .withLoanId(loan.getId()).withClientId(loan.getClientId()).withOfficeId(loan.getOfficeId())
                    .withGroupId(loan.getGroupId()).build();
        }
        catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleDataIntegrityViolation(dve);
            return CommandProcessingResult.empty();
        }
    }
    private void createLoanTermVariationsForRegularLoans(final Loan loan, final Integer graceOnPrincipal, final Integer graceOnInterest,
            final Integer extraTerms, final BigDecimal interestRate, LocalDate rescheduleFromDate, LocalDate adjustedDueDate,
            final LoanRescheduleRequest loanRescheduleRequest,
            List<LoanRescheduleRequestToTermVariationMapping> loanRescheduleRequestToTermVariationMappings, final Boolean isActive,
            final boolean isSpecificToInstallment, BigDecimal decimalValue, LocalDate dueDate, LocalDate endDate, BigDecimal emi) {
        if (rescheduleFromDate != null && endDate != null && emi != null) {
            LoanTermVariations parent = null;
            LocalDate rescheduleFromLocDate = rescheduleFromDate;
            LocalDate endDateLocDate = endDate;
            final Integer termType = LoanTermVariationType.EMI_AMOUNT.getValue();
            List<LoanRepaymentScheduleInstallment> installments = loan.getRepaymentScheduleInstallments();
            for (LoanRepaymentScheduleInstallment installment : installments) {
                if (installment.getDueDate().isEqual(rescheduleFromLocDate) || installment.getDueDate().isEqual(endDateLocDate)
                        || (installment.getDueDate().isAfter(rescheduleFromLocDate) && installment.getDueDate().isBefore(endDateLocDate))) {
                    createLoanTermVariations(loanRescheduleRequest, termType, loan, installment.getDueDate(), installment.getDueDate(),
                            loanRescheduleRequestToTermVariationMappings, isActive, true, emi, parent);
                }
                if (installment.getDueDate().isAfter(endDateLocDate)) {
                    break;
                }
            }
        }
        if (rescheduleFromDate != null && adjustedDueDate != null) {
            LoanTermVariations parent = null;
            final Integer termType = LoanTermVariationType.DUE_DATE.getValue();
            createLoanTermVariations(loanRescheduleRequest, termType, loan, rescheduleFromDate, adjustedDueDate,
                    loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment, decimalValue, parent);
        }
        if (rescheduleFromDate != null && interestRate != null) {
            LoanTermVariations parent = null;
            final Integer termType = LoanTermVariationType.INTEREST_RATE_FROM_INSTALLMENT.getValue();
            createLoanTermVariations(loanRescheduleRequest, termType, loan, rescheduleFromDate, dueDate,
                    loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment, interestRate, parent);
        }
        if (rescheduleFromDate != null && graceOnPrincipal != null) {
            final Integer termType = LoanTermVariationType.GRACE_ON_PRINCIPAL.getValue();
            LoanTermVariations parent = null;
            parent = createLoanTermVariations(loanRescheduleRequest, termType, loan, rescheduleFromDate, dueDate,
                    loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment, BigDecimal.valueOf(graceOnPrincipal),
                    parent);
            BigDecimal extraTermsBasedOnGracePeriods = BigDecimal.valueOf(graceOnPrincipal);
            createLoanTermVariations(loanRescheduleRequest, LoanTermVariationType.EXTEND_REPAYMENT_PERIOD.getValue(), loan,
                    rescheduleFromDate, dueDate, loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment,
                    extraTermsBasedOnGracePeriods, parent);
        }
        if (rescheduleFromDate != null && graceOnInterest != null) {
            LoanTermVariations parent = null;
            final Integer termType = LoanTermVariationType.GRACE_ON_INTEREST.getValue();
            createLoanTermVariations(loanRescheduleRequest, termType, loan, rescheduleFromDate, dueDate,
                    loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment, BigDecimal.valueOf(graceOnInterest),
                    parent);
        }
        if (rescheduleFromDate != null && extraTerms != null) {
            LoanTermVariations parent = null;
            final Integer termType = LoanTermVariationType.EXTEND_REPAYMENT_PERIOD.getValue();
            createLoanTermVariations(loanRescheduleRequest, termType, loan, rescheduleFromDate, dueDate,
                    loanRescheduleRequestToTermVariationMappings, isActive, isSpecificToInstallment, BigDecimal.valueOf(extraTerms),
                    parent);
        }
        loanRescheduleRequest.updateLoanRescheduleRequestToTermVariationMappings(loanRescheduleRequestToTermVariationMappings);
    }
    private LoanTermVariations createLoanTermVariations(LoanRescheduleRequest loanRescheduleRequest, final Integer termType,
            final Loan loan, LocalDate rescheduleFromDate, LocalDate adjustedDueDate,
            List<LoanRescheduleRequestToTermVariationMapping> loanRescheduleRequestToTermVariationMappings, final Boolean isActive,
            final boolean isSpecificToInstallment, final BigDecimal decimalValue, LoanTermVariations parent) {
        LoanTermVariations loanTermVariation = new LoanTermVariations(termType, rescheduleFromDate, decimalValue, adjustedDueDate,
                isSpecificToInstallment, loan, loan.status().getValue(), isActive, parent);
        loanRescheduleRequestToTermVariationMappings
                .add(LoanRescheduleRequestToTermVariationMapping.createNew(loanRescheduleRequest, loanTermVariation));
        return loanTermVariation;
    }
    @Override
    @Transactional
    public CommandProcessingResult approve(JsonCommand jsonCommand) {
        try {
            final Long loanRescheduleRequestId = jsonCommand.entityId();
            final LoanRescheduleRequest loanRescheduleRequest = this.loanRescheduleRequestRepository.findById(loanRescheduleRequestId)
                    .orElseThrow(() -> new LoanRescheduleRequestNotFoundException(loanRescheduleRequestId));
            this.loanRescheduleRequestDataValidator.validateForApproveAction(jsonCommand, loanRescheduleRequest);
            final AppUser appUser = this.platformSecurityContext.authenticatedUser();
            final Map<String, Object> changes = new LinkedHashMap<>();
            LocalDate approvedOnDate = jsonCommand.localDateValueOfParameterNamed("approvedOnDate");
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(jsonCommand.dateFormat())
                    .withLocale(jsonCommand.extractLocale());
            changes.put("locale", jsonCommand.locale());
            changes.put("dateFormat", jsonCommand.dateFormat());
            changes.put("approvedOnDate", approvedOnDate.format(dateTimeFormatter));
            changes.put("approvedByUserId", appUser.getId());
            Loan loan = loanRescheduleRequest.getLoan();
            final List<Long> existingTransactionIds = new ArrayList<>(loan.findExistingTransactionIds());
            final List<Long> existingReversedTransactionIds = new ArrayList<>(loan.findExistingReversedTransactionIds());
            ScheduleGeneratorDTO scheduleGeneratorDTO = this.loanUtilService.buildScheduleGeneratorDTO(loan,
                    loanRescheduleRequest.getRescheduleFromDate());
            Collection<LoanRepaymentScheduleHistory> loanRepaymentScheduleHistoryList = this.loanScheduleHistoryWritePlatformService
                    .createLoanScheduleArchive(loan.getRepaymentScheduleInstallments(), loan, loanRescheduleRequest);
            final LoanApplicationTerms loanApplicationTerms = loan.constructLoanApplicationTerms(scheduleGeneratorDTO);
            LocalDate rescheduleFromDate = null;
            Set<LoanTermVariations> activeLoanTermVariations = loan.getActiveLoanTermVariations();
            LoanTermVariations dueDateVariationInCurrentRequest = loanRescheduleRequest.getDueDateTermVariationIfExists();
            if (dueDateVariationInCurrentRequest != null && activeLoanTermVariations != null) {
                LocalDate fromScheduleDate = dueDateVariationInCurrentRequest.fetchTermApplicaDate();
                LocalDate currentScheduleDate = fromScheduleDate;
                LocalDate modifiedScheduleDate = dueDateVariationInCurrentRequest.fetchDateValue();
                Map<LocalDate, LocalDate> changeMap = new HashMap<>();
                changeMap.put(currentScheduleDate, modifiedScheduleDate);
                for (LoanTermVariations activeLoanTermVariation : activeLoanTermVariations) {
                    if (activeLoanTermVariation.getTermType().isDueDateVariation()
                            && activeLoanTermVariation.fetchDateValue().equals(dueDateVariationInCurrentRequest.fetchTermApplicaDate())) {
                        activeLoanTermVariation.markAsInactive();
                        rescheduleFromDate = activeLoanTermVariation.fetchTermApplicaDate();
                        dueDateVariationInCurrentRequest.setTermApplicableFrom(rescheduleFromDate);
                    } else if (!activeLoanTermVariation.fetchTermApplicaDate().isBefore(fromScheduleDate)) {
                        while (currentScheduleDate.isBefore(activeLoanTermVariation.fetchTermApplicaDate())) {
                            currentScheduleDate = this.scheduledDateGenerator.generateNextRepaymentDate(currentScheduleDate,
                                    loanApplicationTerms, false);
                            modifiedScheduleDate = this.scheduledDateGenerator.generateNextRepaymentDate(modifiedScheduleDate,
                                    loanApplicationTerms, false);
                            changeMap.put(currentScheduleDate, modifiedScheduleDate);
                        }
                        if (changeMap.containsKey(activeLoanTermVariation.fetchTermApplicaDate())) {
                            activeLoanTermVariation.setTermApplicableFrom(changeMap.get(activeLoanTermVariation.fetchTermApplicaDate()));
                        }
                    }
                }
            }
            if (rescheduleFromDate == null) {
                rescheduleFromDate = loanRescheduleRequest.getRescheduleFromDate();
            }
            for (LoanRescheduleRequestToTermVariationMapping mapping : loanRescheduleRequest
                    .getLoanRescheduleRequestToTermVariationMappings()) {
                mapping.getLoanTermVariations().updateIsActive(true);
            }
            BigDecimal annualNominalInterestRate = null;
            List<LoanTermVariationsData> loanTermVariations = new ArrayList<>();
            loan.constructLoanTermVariations(scheduleGeneratorDTO.getFloatingRateDTO(), annualNominalInterestRate, loanTermVariations);
            loanApplicationTerms.getLoanTermVariations().setExceptionData(loanTermVariations);
            final RoundingMode roundingMode = MoneyHelper.getRoundingMode();
            final MathContext mathContext = new MathContext(8, roundingMode);
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor = this.loanRepaymentScheduleTransactionProcessorFactory
                    .determineProcessor(loan.transactionProcessingStrategy());
            final LoanScheduleGenerator loanScheduleGenerator = this.loanScheduleFactory.create(loanApplicationTerms.getInterestMethod());
            final LoanLifecycleStateMachine loanLifecycleStateMachine = null;
            loan.setHelpers(loanLifecycleStateMachine, this.loanSummaryWrapper, this.loanRepaymentScheduleTransactionProcessorFactory);
            final LoanScheduleDTO loanSchedule = loanScheduleGenerator.rescheduleNextInstallments(mathContext, loanApplicationTerms, loan,
                    loanApplicationTerms.getHolidayDetailDTO(), loanRepaymentScheduleTransactionProcessor, rescheduleFromDate);
            loan.updateLoanSchedule(loanSchedule.getInstallments());
            loan.recalculateAllCharges();
            ChangedTransactionDetail changedTransactionDetail = loan.processTransactions();
            for (LoanRepaymentScheduleHistory loanRepaymentScheduleHistory : loanRepaymentScheduleHistoryList) {
                this.loanRepaymentScheduleHistoryRepository.save(loanRepaymentScheduleHistory);
            }
            loan.updateRescheduledByUser(appUser);
            loan.updateRescheduledOnDate(DateUtils.getBusinessLocalDate());
            loanRescheduleRequest.approve(appUser, approvedOnDate);
            saveAndFlushLoanWithDataIntegrityViolationChecks(loan);
            if (changedTransactionDetail != null) {
                for (final Map.Entry<Long, LoanTransaction> mapEntry : changedTransactionDetail.getNewTransactionMappings().entrySet()) {
                    this.loanTransactionRepository.save(mapEntry.getValue());
                    loan.addLoanTransaction(mapEntry.getValue());
                    this.accountTransfersWritePlatformService.updateLoanTransaction(mapEntry.getKey(), mapEntry.getValue());
                }
            }
            postJournalEntries(loan, existingTransactionIds, existingReversedTransactionIds);
            this.loanAccountDomainService.recalculateAccruals(loan, true);
            return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(loanRescheduleRequestId)
                    .withLoanId(loanRescheduleRequest.getLoan().getId()).with(changes).withClientId(loan.getClientId())
                    .withOfficeId(loan.getOfficeId()).withGroupId(loan.getGroupId()).build();
        }
        catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleDataIntegrityViolation(dve);
            return CommandProcessingResult.empty();
        }
    }
    private void saveAndFlushLoanWithDataIntegrityViolationChecks(final Loan loan) {
        try {
            List<LoanRepaymentScheduleInstallment> installments = loan.getRepaymentScheduleInstallments();
            for (LoanRepaymentScheduleInstallment installment : installments) {
                if (installment.getId() == null) {
                    this.repaymentScheduleInstallmentRepository.save(installment);
                }
            }
            this.loanRepositoryWrapper.saveAndFlush(loan);
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            final Throwable realCause = e.getCause();
            final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
            final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("loan.transaction");
            if (realCause.getMessage().toLowerCase().contains("external_id_unique")) {
                baseDataValidator.reset().parameter("externalId").failWithCode("value.must.be.unique");
            }
            if (!dataValidationErrors.isEmpty()) {
                throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                        dataValidationErrors, e);
            }
        }
    }
    private void postJournalEntries(Loan loan, List<Long> existingTransactionIds, List<Long> existingReversedTransactionIds) {
        final MonetaryCurrency currency = loan.getCurrency();
        final ApplicationCurrency applicationCurrency = this.applicationCurrencyRepository.findOneWithNotFoundDetection(currency);
        boolean isAccountTransfer = false;
        final Map<String, Object> accountingBridgeData = loan.deriveAccountingBridgeData(applicationCurrency.toData(),
                existingTransactionIds, existingReversedTransactionIds, isAccountTransfer);
        this.journalEntryWritePlatformService.createJournalEntriesForLoan(accountingBridgeData);
    }
    @Override
    @Transactional
    public CommandProcessingResult reject(JsonCommand jsonCommand) {
        try {
            final Long loanRescheduleRequestId = jsonCommand.entityId();
            final LoanRescheduleRequest loanRescheduleRequest = loanRescheduleRequestRepository.findById(loanRescheduleRequestId)
                    .orElseThrow(() -> new LoanRescheduleRequestNotFoundException(loanRescheduleRequestId));
            this.loanRescheduleRequestDataValidator.validateForRejectAction(jsonCommand, loanRescheduleRequest);
            final AppUser appUser = this.platformSecurityContext.authenticatedUser();
            final Map<String, Object> changes = new LinkedHashMap<>();
            LocalDate rejectedOnDate = jsonCommand.localDateValueOfParameterNamed("rejectedOnDate");
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(jsonCommand.dateFormat())
                    .withLocale(jsonCommand.extractLocale());
            changes.put("locale", jsonCommand.locale());
            changes.put("dateFormat", jsonCommand.dateFormat());
            changes.put("rejectedOnDate", rejectedOnDate.format(dateTimeFormatter));
            changes.put("rejectedByUserId", appUser.getId());
            if (!changes.isEmpty()) {
                loanRescheduleRequest.reject(appUser, rejectedOnDate);
                Set<LoanRescheduleRequestToTermVariationMapping> loanRescheduleRequestToTermVariationMappings = loanRescheduleRequest
                        .getLoanRescheduleRequestToTermVariationMappings();
                for (LoanRescheduleRequestToTermVariationMapping loanRescheduleRequestToTermVariationMapping : loanRescheduleRequestToTermVariationMappings) {
                    loanRescheduleRequestToTermVariationMapping.getLoanTermVariations().markAsInactive();
                }
            }
            return new CommandProcessingResultBuilder().withCommandId(jsonCommand.commandId()).withEntityId(loanRescheduleRequestId)
                    .withLoanId(loanRescheduleRequest.getLoan().getId()).with(changes)
                    .withClientId(loanRescheduleRequest.getLoan().getClientId()).withOfficeId(loanRescheduleRequest.getLoan().getOfficeId())
                    .withGroupId(loanRescheduleRequest.getLoan().getGroupId()).build();
        }
        catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleDataIntegrityViolation(dve);
            return CommandProcessingResult.empty();
        }
    }
    private void handleDataIntegrityViolation(final NonTransientDataAccessException dve) {
        LOG.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.loan.reschedule.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }
}
