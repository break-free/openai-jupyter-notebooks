
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.organisation.monetary.domain.ApplicationCurrency;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.organisation.workingdays.data.AdjustedDateDetailsDTO;
import org.apache.fineract.organisation.workingdays.domain.RepaymentRescheduleType;
import org.apache.fineract.portfolio.calendar.domain.CalendarInstance;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.apache.fineract.portfolio.loanaccount.data.DisbursementData;
import org.apache.fineract.portfolio.loanaccount.data.HolidayDetailDTO;
import org.apache.fineract.portfolio.loanaccount.data.LoanTermVariationsData;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanInterestRecalcualtionAdditionalDetails;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.LoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleDTO;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanScheduleParams;
import org.apache.fineract.portfolio.loanaccount.loanschedule.exception.MultiDisbursementEmiAmountException;
import org.apache.fineract.portfolio.loanaccount.loanschedule.exception.MultiDisbursementOutstandingAmoutException;
import org.apache.fineract.portfolio.loanaccount.loanschedule.exception.ScheduleDateException;
public abstract class AbstractLoanScheduleGenerator implements LoanScheduleGenerator {
    protected final ScheduledDateGenerator scheduledDateGenerator = new DefaultScheduledDateGenerator();
    private final PaymentPeriodsInOneYearCalculator paymentPeriodsInOneYearCalculator = new DefaultPaymentPeriodsInOneYearCalculator();
    @Override
    public LoanScheduleModel generate(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            final Set<LoanCharge> loanCharges, final HolidayDetailDTO holidayDetailDTO) {
        final LoanScheduleParams loanScheduleRecalculationDTO = null;
        return generate(mc, loanApplicationTerms, loanCharges, holidayDetailDTO, loanScheduleRecalculationDTO);
    }
    private LoanScheduleModel generate(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            final Set<LoanCharge> loanCharges, final HolidayDetailDTO holidayDetailDTO, final LoanScheduleParams loanScheduleParams) {
        final ApplicationCurrency applicationCurrency = loanApplicationTerms.getApplicationCurrency();
        LocalDate loanEndDate = this.scheduledDateGenerator.getLastRepaymentDate(loanApplicationTerms, holidayDetailDTO);
        LoanTermVariationsData lastDueDateVariation = loanApplicationTerms.getLoanTermVariations()
                .fetchLoanTermDueDateVariationsData(loanEndDate);
        if (lastDueDateVariation != null) {
            loanEndDate = lastDueDateVariation.getDateValue();
        }
        loanApplicationTerms.updateLoanEndDate(loanEndDate);
        final BigDecimal chargesDueAtTimeOfDisbursement = deriveTotalChargesDueAtTimeOfDisbursement(loanCharges);
        final MonetaryCurrency currency = loanApplicationTerms.getCurrency();
        final int numberOfRepayments = loanApplicationTerms.fetchNumberOfRepaymentsAfterExceptions();
        LoanScheduleParams scheduleParams = null;
        if (loanScheduleParams == null) {
            scheduleParams = LoanScheduleParams.createLoanScheduleParams(currency, Money.of(currency, chargesDueAtTimeOfDisbursement),
                    loanApplicationTerms.getExpectedDisbursementDate(), getPrincipalToBeScheduled(loanApplicationTerms));
        } else if (!loanScheduleParams.isPartialUpdate()) {
            scheduleParams = LoanScheduleParams.createLoanScheduleParams(currency, Money.of(currency, chargesDueAtTimeOfDisbursement),
                    loanApplicationTerms.getExpectedDisbursementDate(), getPrincipalToBeScheduled(loanApplicationTerms),
                    loanScheduleParams);
        } else {
            scheduleParams = loanScheduleParams;
        }
        final Collection<RecalculationDetail> transactions = scheduleParams.getRecalculationDetails();
        final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor = scheduleParams
                .getLoanRepaymentScheduleTransactionProcessor();
        Collection<LoanScheduleModelPeriod> periods = new ArrayList<>();
        if (!scheduleParams.isPartialUpdate()) {
            periods = createNewLoanScheduleListWithDisbursementDetails(numberOfRepayments, loanApplicationTerms,
                    chargesDueAtTimeOfDisbursement);
        }
        if (!scheduleParams.isPartialUpdate() && !loanApplicationTerms.isEqualAmortization()) {
            Money totalInterestChargedForFullLoanTerm = loanApplicationTerms
                    .calculateTotalInterestCharged(this.paymentPeriodsInOneYearCalculator, mc);
            loanApplicationTerms.updateTotalInterestDue(totalInterestChargedForFullLoanTerm);
        }
        boolean isFirstRepayment = true;
        LocalDate firstRepaymentdate = this.scheduledDateGenerator
                .generateNextRepaymentDate(loanApplicationTerms.getExpectedDisbursementDate(), loanApplicationTerms, isFirstRepayment);
        final LocalDate idealDisbursementDate = this.scheduledDateGenerator.idealDisbursementDateBasedOnFirstRepaymentDate(
                loanApplicationTerms.getLoanTermPeriodFrequencyType(), loanApplicationTerms.getRepaymentEvery(), firstRepaymentdate,
                loanApplicationTerms.getLoanCalendar(), loanApplicationTerms.getHolidayDetailDTO(), loanApplicationTerms);
        if (!scheduleParams.isPartialUpdate()) {
            updateAmortization(mc, loanApplicationTerms, scheduleParams.getPeriodNumber(), scheduleParams.getOutstandingBalance());
            if (loanApplicationTerms.isMultiDisburseLoan()) {
                BigDecimal disburseAmt = getDisbursementAmount(loanApplicationTerms, scheduleParams.getPeriodStartDate(), periods,
                        chargesDueAtTimeOfDisbursement, scheduleParams.getDisburseDetailMap(), scheduleParams.applyInterestRecalculation());
                scheduleParams.setPrincipalToBeScheduled(Money.of(currency, disburseAmt));
                loanApplicationTerms.setPrincipal(loanApplicationTerms.getPrincipal().zero().plus(disburseAmt));
                scheduleParams.setOutstandingBalance(Money.of(currency, disburseAmt));
                scheduleParams.setOutstandingBalanceAsPerRest(Money.of(currency, disburseAmt));
            }
        }
        final Set<LoanCharge> nonCompoundingCharges = seperateTotalCompoundingPercentageCharges(loanCharges);
        LocalDate currentDate = DateUtils.getBusinessLocalDate();
        LocalDate lastRestDate = currentDate;
        if (loanApplicationTerms.getRestCalendarInstance() != null) {
            lastRestDate = getNextRestScheduleDate(currentDate.minusDays(1), loanApplicationTerms, holidayDetailDTO);
        }
        boolean isNextRepaymentAvailable = true;
        Boolean extendTermForDailyRepayments = false;
        if (holidayDetailDTO.getWorkingDays().getExtendTermForDailyRepayments() == true
                && loanApplicationTerms.getRepaymentPeriodFrequencyType() == PeriodFrequencyType.DAYS
                && loanApplicationTerms.getRepaymentEvery() == 1) {
            holidayDetailDTO.getWorkingDays().setRepaymentReschedulingType(RepaymentRescheduleType.MOVE_TO_NEXT_WORKING_DAY.getValue());
            extendTermForDailyRepayments = true;
        }
        final Collection<LoanTermVariationsData> interestRates = loanApplicationTerms.getLoanTermVariations().getInterestRateChanges();
        final Collection<LoanTermVariationsData> interestRatesForInstallments = loanApplicationTerms.getLoanTermVariations()
                .getInterestRateFromInstallment();
        if (scheduleParams.isPartialUpdate()) {
            if (loanApplicationTerms.isMultiDisburseLoan()) {
                loanApplicationTerms.setPrincipal(scheduleParams.getPrincipalToBeScheduled());
            }
            applyLoanVariationsForPartialScheduleGenerate(loanApplicationTerms, scheduleParams, interestRates,
                    interestRatesForInstallments);
            if (!firstRepaymentdate.isAfter(scheduleParams.getActualRepaymentDate())) {
                isFirstRepayment = false;
            }
        }
        while (!scheduleParams.getOutstandingBalance().isZero() || !scheduleParams.getDisburseDetailMap().isEmpty()) {
            LocalDate previousRepaymentDate = scheduleParams.getActualRepaymentDate();
            scheduleParams.setActualRepaymentDate(this.scheduledDateGenerator
                    .generateNextRepaymentDate(scheduleParams.getActualRepaymentDate(), loanApplicationTerms, isFirstRepayment));
            AdjustedDateDetailsDTO adjustedDateDetailsDTO = this.scheduledDateGenerator
                    .adjustRepaymentDate(scheduleParams.getActualRepaymentDate(), loanApplicationTerms, holidayDetailDTO);
            scheduleParams.setActualRepaymentDate(adjustedDateDetailsDTO.getChangedActualRepaymentDate());
            isFirstRepayment = false;
            LocalDate scheduledDueDate = adjustedDateDetailsDTO.getChangedScheduleDate();
            LocalDate periodStartDateApplicableForInterest = calculateInterestStartDateForPeriod(loanApplicationTerms,
                    scheduleParams.getPeriodStartDate(), idealDisbursementDate, firstRepaymentdate,
                    loanApplicationTerms.isInterestChargedFromDateSameAsDisbursalDateEnabled(),
                    loanApplicationTerms.getExpectedDisbursementDate());
            LoanTermVariationParams termVariationParams = applyLoanTermVariations(loanApplicationTerms, scheduleParams,
                    previousRepaymentDate, scheduledDueDate, interestRatesForInstallments, this.paymentPeriodsInOneYearCalculator, mc);
            scheduledDueDate = termVariationParams.getScheduledDueDate();
            if (!loanApplicationTerms.isFirstRepaymentDateAllowedOnHoliday()) {
                AdjustedDateDetailsDTO adjustedDateDetailsDTO1 = this.scheduledDateGenerator.adjustRepaymentDate(scheduledDueDate,
                        loanApplicationTerms, holidayDetailDTO);
                scheduledDueDate = adjustedDateDetailsDTO1.getChangedScheduleDate();
            }
            scheduleParams
                    .addLoanTermInDays(Math.toIntExact(ChronoUnit.DAYS.between(scheduleParams.getPeriodStartDate(), scheduledDueDate)));
            if (termVariationParams.isSkipPeriod()) {
                continue;
            }
            if (scheduleParams.getPeriodStartDate().isAfter(scheduledDueDate)) {
                throw new ScheduleDateException("Due date can't be before period start date", scheduledDueDate);
            }
            if (extendTermForDailyRepayments) {
                scheduleParams.setActualRepaymentDate(scheduledDueDate);
            }
            boolean isCompletePeriod = true;
            if (scheduleParams.getScheduleTillDate() != null && !scheduledDueDate.isBefore(scheduleParams.getScheduleTillDate())) {
                if (!scheduledDueDate.isEqual(scheduleParams.getScheduleTillDate())) {
                    isCompletePeriod = false;
                }
                scheduledDueDate = scheduleParams.getScheduleTillDate();
                isNextRepaymentAvailable = false;
            }
            if (loanApplicationTerms.isInterestRecalculationEnabled()) {
                populateCompoundingDatesInPeriod(scheduleParams.getPeriodStartDate(), scheduledDueDate, loanApplicationTerms,
                        holidayDetailDTO, scheduleParams, loanCharges, currency);
            }
            Collection<RecalculationDetail> applicableTransactions = getApplicableTransactionsForPeriod(
                    scheduleParams.applyInterestRecalculation(), scheduledDueDate, transactions);
            final double interestCalculationGraceOnRepaymentPeriodFraction = this.paymentPeriodsInOneYearCalculator
                    .calculatePortionOfRepaymentPeriodInterestChargingGrace(periodStartDateApplicableForInterest, scheduledDueDate,
                            loanApplicationTerms.getInterestChargedFromLocalDate(), loanApplicationTerms.getLoanTermPeriodFrequencyType(),
                            loanApplicationTerms.getRepaymentEvery());
            ScheduleCurrentPeriodParams currentPeriodParams = new ScheduleCurrentPeriodParams(currency,
                    interestCalculationGraceOnRepaymentPeriodFraction);
            if (loanApplicationTerms.isMultiDisburseLoan()) {
                updateBalanceBasedOnDisbursement(loanApplicationTerms, chargesDueAtTimeOfDisbursement, scheduleParams, periods,
                        scheduledDueDate);
            }
            handleRecalculationForNonDueDateTransactions(mc, loanApplicationTerms, loanCharges, holidayDetailDTO, scheduleParams, periods,
                    loanApplicationTerms.getTotalInterestDue(), idealDisbursementDate, firstRepaymentdate, lastRestDate, scheduledDueDate,
                    periodStartDateApplicableForInterest, applicableTransactions, currentPeriodParams);
            if (currentPeriodParams.isSkipCurrentLoop()) {
                continue;
            }
            periodStartDateApplicableForInterest = calculateInterestStartDateForPeriod(loanApplicationTerms,
                    scheduleParams.getPeriodStartDate(), idealDisbursementDate, firstRepaymentdate,
                    loanApplicationTerms.isInterestChargedFromDateSameAsDisbursalDateEnabled(),
                    loanApplicationTerms.getExpectedDisbursementDate());
            updateCompoundingDetails(scheduleParams, periodStartDateApplicableForInterest);
            PrincipalInterest principalInterestForThisPeriod = calculatePrincipalInterestComponentsForPeriod(
                    this.paymentPeriodsInOneYearCalculator, currentPeriodParams.getInterestCalculationGraceOnRepaymentPeriodFraction(),
                    scheduleParams.getTotalCumulativePrincipal().minus(scheduleParams.getReducePrincipal()),
                    scheduleParams.getTotalCumulativeInterest(), loanApplicationTerms.getTotalInterestDue(),
                    scheduleParams.getTotalOutstandingInterestPaymentDueToGrace(), scheduleParams.getOutstandingBalanceAsPerRest(),
                    loanApplicationTerms, scheduleParams.getPeriodNumber(), mc, mergeVariationsToMap(scheduleParams),
                    scheduleParams.getCompoundingMap(), periodStartDateApplicableForInterest, scheduledDueDate, interestRates);
            if (loanApplicationTerms.getFixedEmiAmount() != null
                    && loanApplicationTerms.getFixedEmiAmount().compareTo(principalInterestForThisPeriod.interest().getAmount()) < 0) {
                String errorMsg = "EMI amount must be greater than : " + principalInterestForThisPeriod.interest().getAmount();
                throw new MultiDisbursementEmiAmountException(errorMsg, principalInterestForThisPeriod.interest().getAmount(),
                        loanApplicationTerms.getFixedEmiAmount());
            }
            currentPeriodParams.setInterestForThisPeriod(principalInterestForThisPeriod.interest());
            Money lastTotalOutstandingInterestPaymentDueToGrace = scheduleParams.getTotalOutstandingInterestPaymentDueToGrace();
            scheduleParams.setTotalOutstandingInterestPaymentDueToGrace(principalInterestForThisPeriod.interestPaymentDueToGrace());
            currentPeriodParams.setPrincipalForThisPeriod(principalInterestForThisPeriod.principal());
            updatePrincipalPortionBasedOnPreviousEarlyPayments(currency, scheduleParams, currentPeriodParams);
            updateAmountsBasedOnCurrentEarlyPayments(mc, loanApplicationTerms, scheduleParams, currentPeriodParams);
            if (scheduleParams.getOutstandingBalance().isLessThanZero() || !isNextRepaymentAvailable) {
                currentPeriodParams.plusPrincipalForThisPeriod(scheduleParams.getOutstandingBalance());
                scheduleParams.setOutstandingBalance(Money.zero(currency));
            }
            if (!isNextRepaymentAvailable) {
                scheduleParams.getDisburseDetailMap().clear();
            }
            applyChargesForCurrentPeriod(loanCharges, currency, scheduleParams, scheduledDueDate, currentPeriodParams);
            final Money totalInstallmentDue = currentPeriodParams.fetchTotalAmountForPeriod();
            if (currentPeriodParams.getLastInstallment() != null && currentPeriodParams.getPrincipalForThisPeriod().isZero()) {
                currentPeriodParams.getLastInstallment().addInterestAmount(currentPeriodParams.getInterestForThisPeriod());
                continue;
            }
            LoanScheduleModelPeriod installment = LoanScheduleModelRepaymentPeriod.repayment(scheduleParams.getInstalmentNumber(),
                    scheduleParams.getPeriodStartDate(), scheduledDueDate, currentPeriodParams.getPrincipalForThisPeriod(),
                    scheduleParams.getOutstandingBalance(), currentPeriodParams.getInterestForThisPeriod(),
                    currentPeriodParams.getFeeChargesForInstallment(), currentPeriodParams.getPenaltyChargesForInstallment(),
                    totalInstallmentDue, !isCompletePeriod);
            if (principalInterestForThisPeriod.getRescheduleInterestPortion() != null) {
                installment.setRescheduleInterestPortion(principalInterestForThisPeriod.getRescheduleInterestPortion().getAmount());
            }
            addLoanRepaymentScheduleInstallment(scheduleParams.getInstallments(), installment);
            installment = handleRecalculationForTransactions(mc, loanApplicationTerms, holidayDetailDTO, currency, scheduleParams,
                    loanRepaymentScheduleTransactionProcessor, loanApplicationTerms.getTotalInterestDue(), lastRestDate, scheduledDueDate,
                    periodStartDateApplicableForInterest, applicableTransactions, currentPeriodParams,
                    lastTotalOutstandingInterestPaymentDueToGrace, installment, loanCharges);
            if (loanApplicationTerms.getCurrentPeriodFixedEmiAmount() != null) {
                installment.setEMIFixedSpecificToInstallmentTrue();
            }
            periods.add(installment);
            updateAmountsWithEffectiveDate(loanApplicationTerms, holidayDetailDTO, scheduleParams, scheduledDueDate, currentPeriodParams,
                    installment, lastRestDate);
            scheduleParams.addTotalCumulativePrincipal(currentPeriodParams.getPrincipalForThisPeriod());
            scheduleParams.addTotalRepaymentExpected(totalInstallmentDue);
            scheduleParams.addTotalCumulativeInterest(currentPeriodParams.getInterestForThisPeriod());
            scheduleParams.setPeriodStartDate(scheduledDueDate);
            scheduleParams.incrementInstalmentNumber();
            scheduleParams.incrementPeriodNumber();
            if (termVariationParams.isRecalculateAmounts()) {
                loanApplicationTerms.setCurrentPeriodFixedEmiAmount(null);
                loanApplicationTerms.setCurrentPeriodFixedPrincipalAmount(null);
                adjustInstallmentOrPrincipalAmount(loanApplicationTerms, scheduleParams.getTotalCumulativePrincipal(),
                        scheduleParams.getPeriodNumber(), mc);
            }
        }
        if (scheduleParams.getTotalOutstandingInterestPaymentDueToGrace().isGreaterThanZero()) {
            LoanScheduleModelPeriod installment = ((List<LoanScheduleModelPeriod>) periods).get(periods.size() - 1);
            installment.addInterestAmount(scheduleParams.getTotalOutstandingInterestPaymentDueToGrace());
            scheduleParams.addTotalRepaymentExpected(scheduleParams.getTotalOutstandingInterestPaymentDueToGrace());
            scheduleParams.addTotalCumulativeInterest(scheduleParams.getTotalOutstandingInterestPaymentDueToGrace());
            scheduleParams.setTotalOutstandingInterestPaymentDueToGrace(Money.zero(currency));
        }
        updatePeriodsWithCharges(currency, scheduleParams, periods, nonCompoundingCharges);
        if (scheduleParams.getScheduleTillDate() != null) {
            currentDate = scheduleParams.getScheduleTillDate();
        }
        if (scheduleParams.applyInterestRecalculation() && scheduleParams.getLatePaymentMap().size() > 0
                && currentDate.isAfter(scheduleParams.getPeriodStartDate())) {
            Money totalInterest = addInterestOnlyRepaymentScheduleForCurrentdate(mc, loanApplicationTerms, holidayDetailDTO, currency,
                    periods, currentDate, loanRepaymentScheduleTransactionProcessor, transactions, loanCharges, scheduleParams);
            scheduleParams.addTotalCumulativeInterest(totalInterest);
        }
        loanApplicationTerms.resetFixedEmiAmount();
        final BigDecimal totalPrincipalPaid = BigDecimal.ZERO;
        final BigDecimal totalOutstanding = BigDecimal.ZERO;
        updateCompoundingDetails(periods, scheduleParams, loanApplicationTerms);
        return LoanScheduleModel.from(periods, applicationCurrency, scheduleParams.getLoanTermInDays(),
                scheduleParams.getPrincipalToBeScheduled(), scheduleParams.getTotalCumulativePrincipal().getAmount(), totalPrincipalPaid,
                scheduleParams.getTotalCumulativeInterest().getAmount(), scheduleParams.getTotalFeeChargesCharged().getAmount(),
                scheduleParams.getTotalPenaltyChargesCharged().getAmount(), scheduleParams.getTotalRepaymentExpected().getAmount(),
                totalOutstanding);
    }
    private void updateCompoundingDetails(final Collection<LoanScheduleModelPeriod> periods, final LoanScheduleParams params,
            final LoanApplicationTerms loanApplicationTerms) {
        final Map<LocalDate, Map<LocalDate, Money>> compoundingDetails = params.getCompoundingDateVariations();
        if (compoundingDetails.isEmpty()) {
            return;
        }
        for (LoanScheduleModelPeriod loanScheduleModelPeriod : periods) {
            if (loanScheduleModelPeriod.isRepaymentPeriod() && loanScheduleModelPeriod.getLoanCompoundingDetails().isEmpty()) {
                Map<LocalDate, Money> periodCompoundingDetails = compoundingDetails.get(loanScheduleModelPeriod.periodFromDate());
                if (periodCompoundingDetails != null) {
                    for (Map.Entry<LocalDate, Money> entry : periodCompoundingDetails.entrySet()) {
                        if (entry.getValue().isGreaterThanZero() && !entry.getKey().isAfter(loanScheduleModelPeriod.periodDueDate())) {
                            LocalDate effectiveDate = entry.getKey();
                            if (loanApplicationTerms.allowCompoundingOnEod()) {
                                effectiveDate = effectiveDate.minusDays(1);
                            }
                            LoanInterestRecalcualtionAdditionalDetails additionalDetails = new LoanInterestRecalcualtionAdditionalDetails(
                                    effectiveDate, entry.getValue().getAmount());
                            loanScheduleModelPeriod.getLoanCompoundingDetails().add(additionalDetails);
                        }
                    }
                }
            }
        }
    }
    private void applyChargesForCurrentPeriod(final Set<LoanCharge> loanCharges, final MonetaryCurrency currency,
            LoanScheduleParams scheduleParams, LocalDate scheduledDueDate, ScheduleCurrentPeriodParams currentPeriodParams) {
        PrincipalInterest principalInterest = new PrincipalInterest(currentPeriodParams.getPrincipalForThisPeriod(),
                currentPeriodParams.getInterestForThisPeriod(), null);
        currentPeriodParams.setFeeChargesForInstallment(
                cumulativeFeeChargesDueWithin(scheduleParams.getPeriodStartDate(), scheduledDueDate, loanCharges, currency,
                        principalInterest, scheduleParams.getPrincipalToBeScheduled(), scheduleParams.getTotalCumulativeInterest(), true));
        currentPeriodParams.setPenaltyChargesForInstallment(
                cumulativePenaltyChargesDueWithin(scheduleParams.getPeriodStartDate(), scheduledDueDate, loanCharges, currency,
                        principalInterest, scheduleParams.getPrincipalToBeScheduled(), scheduleParams.getTotalCumulativeInterest(), true));
        scheduleParams.addTotalFeeChargesCharged(currentPeriodParams.getFeeChargesForInstallment());
        scheduleParams.addTotalPenaltyChargesCharged(currentPeriodParams.getPenaltyChargesForInstallment());
    }
    private void updatePeriodsWithCharges(final MonetaryCurrency currency, LoanScheduleParams scheduleParams,
            final Collection<LoanScheduleModelPeriod> periods, final Set<LoanCharge> nonCompoundingCharges) {
        for (LoanScheduleModelPeriod loanScheduleModelPeriod : periods) {
            if (loanScheduleModelPeriod.isRepaymentPeriod()) {
                PrincipalInterest principalInterest = new PrincipalInterest(Money.of(currency, loanScheduleModelPeriod.principalDue()),
                        Money.of(currency, loanScheduleModelPeriod.interestDue()), null);
                Money feeChargesForInstallment = cumulativeFeeChargesDueWithin(loanScheduleModelPeriod.periodFromDate(),
                        loanScheduleModelPeriod.periodDueDate(), nonCompoundingCharges, currency, principalInterest,
                        scheduleParams.getPrincipalToBeScheduled(), scheduleParams.getTotalCumulativeInterest(),
                        !loanScheduleModelPeriod.isRecalculatedInterestComponent());
                Money penaltyChargesForInstallment = cumulativePenaltyChargesDueWithin(loanScheduleModelPeriod.periodFromDate(),
                        loanScheduleModelPeriod.periodDueDate(), nonCompoundingCharges, currency, principalInterest,
                        scheduleParams.getPrincipalToBeScheduled(), scheduleParams.getTotalCumulativeInterest(),
                        !loanScheduleModelPeriod.isRecalculatedInterestComponent());
                scheduleParams.addTotalFeeChargesCharged(feeChargesForInstallment);
                scheduleParams.addTotalPenaltyChargesCharged(penaltyChargesForInstallment);
                scheduleParams.addTotalRepaymentExpected(feeChargesForInstallment.plus(penaltyChargesForInstallment));
                loanScheduleModelPeriod.addLoanCharges(feeChargesForInstallment.getAmount(), penaltyChargesForInstallment.getAmount());
            }
        }
    }
    private void updateAmountsWithEffectiveDate(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            LoanScheduleParams scheduleParams, LocalDate scheduledDueDate, ScheduleCurrentPeriodParams currentPeriodParams,
            LoanScheduleModelPeriod installment, LocalDate lastRestDate) {
        LocalDate amountApplicableDate = installment.periodDueDate();
        if (loanApplicationTerms.isInterestRecalculationEnabled()) {
            amountApplicableDate = getNextRestScheduleDate(installment.periodDueDate().minusDays(1), loanApplicationTerms,
                    holidayDetailDTO);
        }
        updateMapWithAmount(scheduleParams.getPrincipalPortionMap(),
                currentPeriodParams.getPrincipalForThisPeriod().minus(currentPeriodParams.getReducedBalance()), amountApplicableDate);
        updateCompoundingMap(loanApplicationTerms, holidayDetailDTO, scheduleParams, lastRestDate, scheduledDueDate);
        updateOutstandingBalanceAsPerRest(scheduleParams, scheduledDueDate);
    }
    private LoanScheduleModelPeriod handleRecalculationForTransactions(final MathContext mc,
            final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO, final MonetaryCurrency currency,
            final LoanScheduleParams scheduleParams,
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor,
            final Money totalInterestChargedForFullLoanTerm, final LocalDate lastRestDate, final LocalDate scheduledDueDate,
            final LocalDate periodStartDateApplicableForInterest, final Collection<RecalculationDetail> applicableTransactions,
            final ScheduleCurrentPeriodParams currentPeriodParams, final Money lastTotalOutstandingInterestPaymentDueToGrace,
            final LoanScheduleModelPeriod installment, Set<LoanCharge> loanCharges) {
        LoanScheduleModelPeriod modifiedInstallment = installment;
        if (scheduleParams.applyInterestRecalculation() && loanRepaymentScheduleTransactionProcessor != null) {
            Money principalProcessed = Money.zero(currency);
            for (RecalculationDetail detail : applicableTransactions) {
                if (!detail.isProcessed()) {
                    LocalDate transactionDate = detail.getTransactionDate();
                    List<LoanTransaction> currentTransactions = new ArrayList<>(2);
                    currentTransactions.add(detail.getTransaction());
                    Money unprocessed = loanRepaymentScheduleTransactionProcessor.handleRepaymentSchedule(currentTransactions, currency,
                            scheduleParams.getInstallments());
                    if (unprocessed.isGreaterThanZero()) {
                        scheduleParams.reduceOutstandingBalance(unprocessed);
                        modifiedInstallment = handlePrepaymentOfLoan(mc, loanApplicationTerms, holidayDetailDTO, scheduleParams,
                                totalInterestChargedForFullLoanTerm, scheduledDueDate, periodStartDateApplicableForInterest,
                                currentPeriodParams.getInterestCalculationGraceOnRepaymentPeriodFraction(), currentPeriodParams,
                                lastTotalOutstandingInterestPaymentDueToGrace, transactionDate, modifiedInstallment, loanCharges);
                        Money addToPrinciapal = Money.zero(currency);
                        if (scheduleParams.getOutstandingBalance().isLessThanZero()) {
                            addToPrinciapal = addToPrinciapal.plus(scheduleParams.getOutstandingBalance());
                            scheduleParams.setOutstandingBalance(Money.zero(currency));
                        }
                        updateAmountsBasedOnEarlyPayment(loanApplicationTerms, holidayDetailDTO, scheduleParams, modifiedInstallment,
                                detail, unprocessed, addToPrinciapal);
                        scheduleParams.addReducePrincipal(unprocessed);
                        currentPeriodParams.plusPrincipalForThisPeriod(unprocessed.plus(addToPrinciapal));
                        principalProcessed = principalProcessed.plus(unprocessed.plus(addToPrinciapal));
                        BigDecimal fixedEmiAmount = loanApplicationTerms.getFixedEmiAmount();
                        scheduleParams
                                .setReducePrincipal(applyEarlyPaymentStrategy(loanApplicationTerms, scheduleParams.getReducePrincipal(),
                                        scheduleParams.getTotalCumulativePrincipal()
                                                .plus(currentPeriodParams.getPrincipalForThisPeriod().minus(principalProcessed)),
                                        scheduleParams.getPeriodNumber() + 1, mc));
                        if (loanApplicationTerms.getAmortizationMethod().isEqualInstallment() && fixedEmiAmount != null
                                && fixedEmiAmount.compareTo(loanApplicationTerms.getFixedEmiAmount()) != 0) {
                            currentPeriodParams.setEmiAmountChanged(true);
                        }
                    }
                    adjustCompoundedAmountWithPaidDetail(scheduleParams, lastRestDate, currentTransactions, loanApplicationTerms,
                            holidayDetailDTO);
                }
            }
            updateLatePaymentsToMap(loanApplicationTerms, holidayDetailDTO, currency, scheduleParams.getLatePaymentMap(), scheduledDueDate,
                    scheduleParams.getInstallments(), true, lastRestDate);
            currentPeriodParams.minusPrincipalForThisPeriod(principalProcessed);
        }
        return modifiedInstallment;
    }
    private LoanScheduleModelPeriod handlePrepaymentOfLoan(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            final HolidayDetailDTO holidayDetailDTO, final LoanScheduleParams scheduleParams,
            final Money totalInterestChargedForFullLoanTerm, final LocalDate scheduledDueDate,
            LocalDate periodStartDateApplicableForInterest, final double interestCalculationGraceOnRepaymentPeriodFraction,
            final ScheduleCurrentPeriodParams currentPeriodParams, final Money lastTotalOutstandingInterestPaymentDueToGrace,
            final LocalDate transactionDate, final LoanScheduleModelPeriod installment, Set<LoanCharge> loanCharges) {
        LoanScheduleModelPeriod modifiedInstallment = installment;
        Money oustanding = scheduleParams.getOutstandingBalance();
        PrincipalInterest tempPrincipalInterest = new PrincipalInterest(currentPeriodParams.getPrincipalForThisPeriod(),
                currentPeriodParams.getInterestForThisPeriod(), null);
        oustanding = oustanding.minus(cumulativeFeeChargesDueWithin(transactionDate, scheduledDueDate, loanCharges,
                totalInterestChargedForFullLoanTerm.getCurrency(), tempPrincipalInterest, scheduleParams.getPrincipalToBeScheduled(),
                scheduleParams.getTotalCumulativeInterest(), true));
        oustanding = oustanding.minus(cumulativePenaltyChargesDueWithin(transactionDate, scheduledDueDate, loanCharges,
                totalInterestChargedForFullLoanTerm.getCurrency(), tempPrincipalInterest, scheduleParams.getPrincipalToBeScheduled(),
                scheduleParams.getTotalCumulativeInterest(), true));
        if (!oustanding.isGreaterThan(currentPeriodParams.getInterestForThisPeriod()) && !scheduledDueDate.equals(transactionDate)) {
            final Collection<LoanTermVariationsData> interestRates = loanApplicationTerms.getLoanTermVariations().getInterestRateChanges();
            LocalDate calculateTill = transactionDate;
            if (loanApplicationTerms.getPreClosureInterestCalculationStrategy().calculateTillRestFrequencyEnabled()) {
                calculateTill = getNextRestScheduleDate(calculateTill.minusDays(1), loanApplicationTerms, holidayDetailDTO);
            }
            if (scheduleParams.getCompoundingDateVariations().containsKey(periodStartDateApplicableForInterest)) {
                scheduleParams.getCompoundingMap().clear();
                scheduleParams.getCompoundingMap()
                        .putAll(scheduleParams.getCompoundingDateVariations().get(periodStartDateApplicableForInterest));
            }
            if (currentPeriodParams.isEmiAmountChanged()) {
                updateFixedInstallmentAmount(mc, loanApplicationTerms, scheduleParams.getPeriodNumber(),
                        loanApplicationTerms.getPrincipal().minus(scheduleParams.getTotalCumulativePrincipal()));
            }
            scheduleParams.getCompoundingDateVariations().put(periodStartDateApplicableForInterest,
                    new TreeMap<>(scheduleParams.getCompoundingMap()));
            scheduleParams.getCompoundingMap().clear();
            populateCompoundingDatesInPeriod(periodStartDateApplicableForInterest, calculateTill, loanApplicationTerms, holidayDetailDTO,
                    scheduleParams, loanCharges, totalInterestChargedForFullLoanTerm.getCurrency());
            BigDecimal currentInterestRate = loanApplicationTerms.getAnnualNominalInterestRate();
            for (LoanTermVariationsData interestRate : interestRates) {
                if (interestRate.isApplicable(periodStartDateApplicableForInterest)) {
                    loanApplicationTerms.updateAnnualNominalInterestRate(interestRate.getDecimalValue());
                }
            }
            PrincipalInterest interestTillDate = calculatePrincipalInterestComponentsForPeriod(this.paymentPeriodsInOneYearCalculator,
                    interestCalculationGraceOnRepaymentPeriodFraction, scheduleParams.getTotalCumulativePrincipal(),
                    scheduleParams.getTotalCumulativeInterest(), totalInterestChargedForFullLoanTerm,
                    lastTotalOutstandingInterestPaymentDueToGrace, scheduleParams.getOutstandingBalanceAsPerRest(), loanApplicationTerms,
                    scheduleParams.getPeriodNumber(), mc, mergeVariationsToMap(scheduleParams), scheduleParams.getCompoundingMap(),
                    periodStartDateApplicableForInterest, calculateTill, interestRates);
            loanApplicationTerms.updateAnnualNominalInterestRate(currentInterestRate);
            final ScheduleCurrentPeriodParams tempPeriod = new ScheduleCurrentPeriodParams(
                    totalInterestChargedForFullLoanTerm.getCurrency(), interestCalculationGraceOnRepaymentPeriodFraction);
            tempPeriod.setInterestForThisPeriod(interestTillDate.interest());
            applyChargesForCurrentPeriod(loanCharges, totalInterestChargedForFullLoanTerm.getCurrency(), scheduleParams, calculateTill,
                    tempPeriod);
            Money interestDiff = currentPeriodParams.getInterestForThisPeriod().minus(tempPeriod.getInterestForThisPeriod());
            Money chargeDiff = currentPeriodParams.getFeeChargesForInstallment().minus(tempPeriod.getFeeChargesForInstallment());
            Money penaltyDiff = currentPeriodParams.getPenaltyChargesForInstallment().minus(tempPeriod.getPenaltyChargesForInstallment());
            Money diff = interestDiff.plus(chargeDiff).plus(penaltyDiff);
            if (scheduleParams.getOutstandingBalance().minus(diff).isGreaterThanZero()) {
                updateCompoundingDetails(scheduleParams, periodStartDateApplicableForInterest);
            } else {
                scheduleParams.reduceOutstandingBalance(diff);
                currentPeriodParams.minusInterestForThisPeriod(interestDiff);
                currentPeriodParams.minusFeeChargesForInstallment(chargeDiff);
                currentPeriodParams.minusPenaltyChargesForInstallment(penaltyDiff);
                currentPeriodParams.plusPrincipalForThisPeriod(diff);
                modifiedInstallment = LoanScheduleModelRepaymentPeriod.repayment(scheduleParams.getInstalmentNumber(),
                        scheduleParams.getPeriodStartDate(), transactionDate, currentPeriodParams.getPrincipalForThisPeriod(),
                        scheduleParams.getOutstandingBalance(), currentPeriodParams.getInterestForThisPeriod(),
                        currentPeriodParams.getFeeChargesForInstallment(), currentPeriodParams.getPenaltyChargesForInstallment(),
                        currentPeriodParams.fetchTotalAmountForPeriod(), false);
                scheduleParams.setTotalOutstandingInterestPaymentDueToGrace(interestTillDate.interestPaymentDueToGrace());
            }
        }
        return modifiedInstallment;
    }
    private void updateAmountsBasedOnCurrentEarlyPayments(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            LoanScheduleParams scheduleParams, ScheduleCurrentPeriodParams currentPeriodParams) {
        currentPeriodParams.setReducedBalance(currentPeriodParams.getEarlyPaidAmount());
        currentPeriodParams.minusEarlyPaidAmount(currentPeriodParams.getPrincipalForThisPeriod());
        if (currentPeriodParams.getEarlyPaidAmount().isGreaterThanZero()) {
            scheduleParams.addReducePrincipal(currentPeriodParams.getEarlyPaidAmount());
            BigDecimal fixedEmiAmount = loanApplicationTerms.getFixedEmiAmount();
            scheduleParams.setReducePrincipal(applyEarlyPaymentStrategy(
                    loanApplicationTerms, scheduleParams.getReducePrincipal(), scheduleParams.getTotalCumulativePrincipal()
                            .plus(currentPeriodParams.getPrincipalForThisPeriod()).plus(currentPeriodParams.getEarlyPaidAmount()),
                    scheduleParams.getPeriodNumber() + 1, mc));
            if (loanApplicationTerms.getAmortizationMethod().isEqualInstallment() && fixedEmiAmount != null
                    && fixedEmiAmount.compareTo(loanApplicationTerms.getFixedEmiAmount()) != 0) {
                currentPeriodParams.setEmiAmountChanged(true);
            }
            currentPeriodParams.plusPrincipalForThisPeriod(currentPeriodParams.getEarlyPaidAmount());
        }
        scheduleParams
                .reduceOutstandingBalance(currentPeriodParams.getPrincipalForThisPeriod().minus(currentPeriodParams.getReducedBalance()));
    }
    private void updatePrincipalPortionBasedOnPreviousEarlyPayments(final MonetaryCurrency currency,
            final LoanScheduleParams scheduleParams, final ScheduleCurrentPeriodParams currentPeriodParams) {
        if (currentPeriodParams.getPrincipalForThisPeriod().isGreaterThan(scheduleParams.getReducePrincipal())) {
            currentPeriodParams.minusPrincipalForThisPeriod(scheduleParams.getReducePrincipal());
            scheduleParams.setReducePrincipal(Money.zero(currency));
        } else {
            scheduleParams.reduceReducePrincipal(currentPeriodParams.getPrincipalForThisPeriod());
            currentPeriodParams.setPrincipalForThisPeriod(Money.zero(currency));
        }
    }
    private void updateCompoundingDetails(LoanScheduleParams scheduleParams, LocalDate periodStartDateApplicableForInterest) {
        if (scheduleParams.getCompoundingDateVariations().containsKey(periodStartDateApplicableForInterest)) {
            scheduleParams.getCompoundingMap().clear();
            scheduleParams.getCompoundingMap()
                    .putAll(scheduleParams.getCompoundingDateVariations().get(periodStartDateApplicableForInterest));
            scheduleParams.getCompoundingDateVariations().remove(periodStartDateApplicableForInterest);
        }
    }
    private void handleRecalculationForNonDueDateTransactions(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            final Set<LoanCharge> loanCharges, final HolidayDetailDTO holidayDetailDTO, LoanScheduleParams scheduleParams,
            final Collection<LoanScheduleModelPeriod> periods, final Money totalInterestChargedForFullLoanTerm,
            final LocalDate idealDisbursementDate, LocalDate firstRepaymentdate, final LocalDate lastRestDate,
            final LocalDate scheduledDueDate, final LocalDate periodStartDateForInterest,
            final Collection<RecalculationDetail> applicableTransactions, final ScheduleCurrentPeriodParams currentPeriodParams) {
        if (scheduleParams.applyInterestRecalculation()) {
            final MonetaryCurrency currency = scheduleParams.getCurrency();
            final Collection<LoanTermVariationsData> interestRates = loanApplicationTerms.getLoanTermVariations().getInterestRateChanges();
            boolean checkForOutstanding = true;
            List<RecalculationDetail> unprocessedTransactions = new ArrayList<>();
            List<RecalculationDetail> processTransactions = new ArrayList<>();
            LoanScheduleModelPeriod installment = null;
            LocalDate periodStartDateApplicableForInterest = periodStartDateForInterest;
            for (RecalculationDetail detail : applicableTransactions) {
                if (detail.isProcessed()) {
                    continue;
                }
                boolean updateLatePaymentMap = false;
                final LocalDate transactionDate = detail.getTransactionDate();
                if (transactionDate.isBefore(scheduledDueDate)) {
                    if (scheduleParams.getLoanRepaymentScheduleTransactionProcessor() != null && scheduleParams
                            .getLoanRepaymentScheduleTransactionProcessor().isInterestFirstRepaymentScheduleTransactionProcessor()) {
                        if (detail.getTransaction().isWaiver()) {
                            processTransactions.add(detail);
                            continue;
                        }
                        List<LoanTransaction> currentTransactions = new ArrayList<>();
                        for (RecalculationDetail processDetail : processTransactions) {
                            currentTransactions.addAll(createCurrentTransactionList(processDetail));
                        }
                        processTransactions.clear();
                        currentTransactions.addAll(createCurrentTransactionList(detail));
                        if (!transactionDate.isEqual(scheduleParams.getPeriodStartDate()) || scheduleParams.getInstalmentNumber() == 1) {
                            int periodDays = Math.toIntExact(ChronoUnit.DAYS.between(scheduleParams.getPeriodStartDate(), transactionDate));
                            periodStartDateApplicableForInterest = calculateInterestStartDateForPeriod(loanApplicationTerms,
                                    scheduleParams.getPeriodStartDate(), idealDisbursementDate, firstRepaymentdate,
                                    loanApplicationTerms.isInterestChargedFromDateSameAsDisbursalDateEnabled(),
                                    loanApplicationTerms.getExpectedDisbursementDate());
                            int daysInPeriodApplicable = Math
                                    .toIntExact(ChronoUnit.DAYS.between(periodStartDateApplicableForInterest, transactionDate));
                            Money interestForThisinstallment = Money.zero(currency);
                            if (daysInPeriodApplicable > 0) {
                                PrincipalInterest principalInterestForThisPeriod = calculatePrincipalInterestComponentsForPeriod(
                                        this.paymentPeriodsInOneYearCalculator,
                                        currentPeriodParams.getInterestCalculationGraceOnRepaymentPeriodFraction(),
                                        scheduleParams.getTotalCumulativePrincipal().minus(scheduleParams.getReducePrincipal()),
                                        scheduleParams.getTotalCumulativeInterest(), totalInterestChargedForFullLoanTerm,
                                        scheduleParams.getTotalOutstandingInterestPaymentDueToGrace(),
                                        scheduleParams.getOutstandingBalanceAsPerRest(), loanApplicationTerms,
                                        scheduleParams.getPeriodNumber(), mc, mergeVariationsToMap(scheduleParams),
                                        scheduleParams.getCompoundingMap(), periodStartDateApplicableForInterest, transactionDate,
                                        interestRates);
                                interestForThisinstallment = principalInterestForThisPeriod.interest();
                                scheduleParams.setTotalOutstandingInterestPaymentDueToGrace(
                                        principalInterestForThisPeriod.interestPaymentDueToGrace());
                            }
                            Money principalForThisPeriod = Money.zero(currency);
                            PrincipalInterest principalInterest = new PrincipalInterest(principalForThisPeriod, interestForThisinstallment,
                                    null);
                            Money feeChargesForInstallment = cumulativeFeeChargesDueWithin(scheduleParams.getPeriodStartDate(),
                                    transactionDate, loanCharges, currency, principalInterest, scheduleParams.getPrincipalToBeScheduled(),
                                    scheduleParams.getTotalCumulativeInterest(), false);
                            Money penaltyChargesForInstallment = cumulativePenaltyChargesDueWithin(scheduleParams.getPeriodStartDate(),
                                    transactionDate, loanCharges, currency, principalInterest, scheduleParams.getPrincipalToBeScheduled(),
                                    scheduleParams.getTotalCumulativeInterest(), false);
                            final Money totalInstallmentDue = principalForThisPeriod.plus(interestForThisinstallment)
                                    .plus(feeChargesForInstallment).plus(penaltyChargesForInstallment);
                            installment = LoanScheduleModelRepaymentPeriod.repayment(scheduleParams.getInstalmentNumber(),
                                    scheduleParams.getPeriodStartDate(), transactionDate, principalForThisPeriod,
                                    scheduleParams.getOutstandingBalance(), interestForThisinstallment, feeChargesForInstallment,
                                    penaltyChargesForInstallment, totalInstallmentDue, true);
                            periods.add(installment);
                            addLoanRepaymentScheduleInstallment(scheduleParams.getInstallments(), installment);
                            updateCompoundingMap(loanApplicationTerms, holidayDetailDTO, scheduleParams, lastRestDate, scheduledDueDate);
                            updateOutstandingBalanceAsPerRest(scheduleParams, transactionDate);
                            scheduleParams.addLoanTermInDays(periodDays);
                            scheduleParams.addTotalRepaymentExpected(totalInstallmentDue);
                            scheduleParams.addTotalCumulativeInterest(interestForThisinstallment);
                            scheduleParams.addTotalFeeChargesCharged(feeChargesForInstallment);
                            scheduleParams.addTotalPenaltyChargesCharged(penaltyChargesForInstallment);
                            scheduleParams.setPeriodStartDate(transactionDate);
                            periodStartDateApplicableForInterest = scheduleParams.getPeriodStartDate();
                            updateLatePaymentMap = true;
                            scheduleParams.incrementInstalmentNumber();
                            populateCompoundingDatesInPeriod(scheduleParams.getPeriodStartDate(), scheduledDueDate, loanApplicationTerms,
                                    holidayDetailDTO, scheduleParams, loanCharges, currency);
                        } else if (installment == null) {
                            installment = ((List<LoanScheduleModelPeriod>) periods).get(periods.size() - 1);
                        }
                        Money unprocessed = scheduleParams.getLoanRepaymentScheduleTransactionProcessor()
                                .handleRepaymentSchedule(currentTransactions, currency, scheduleParams.getInstallments());
                        if (unprocessed.isGreaterThanZero()) {
                            if (loanApplicationTerms.getPreClosureInterestCalculationStrategy().calculateTillRestFrequencyEnabled()) {
                                LocalDate applicableDate = getNextRestScheduleDate(transactionDate.minusDays(1), loanApplicationTerms,
                                        holidayDetailDTO);
                                checkForOutstanding = transactionDate.isEqual(applicableDate);
                            }
                            scheduleParams.reduceOutstandingBalance(unprocessed);
                            Money addToPrinciapal = Money.zero(currency);
                            if (!scheduleParams.getOutstandingBalance().isGreaterThanZero()) {
                                addToPrinciapal = addToPrinciapal.plus(scheduleParams.getOutstandingBalance());
                                scheduleParams.setOutstandingBalance(Money.zero(currency));
                                currentPeriodParams.setLastInstallment(installment);
                            }
                            updateAmountsBasedOnEarlyPayment(loanApplicationTerms, holidayDetailDTO, scheduleParams, installment, detail,
                                    unprocessed, addToPrinciapal);
                            scheduleParams.addReducePrincipal(unprocessed);
                            scheduleParams
                                    .setReducePrincipal(applyEarlyPaymentStrategy(loanApplicationTerms, scheduleParams.getReducePrincipal(),
                                            scheduleParams.getTotalCumulativePrincipal(), scheduleParams.getPeriodNumber(), mc));
                        }
                        handleLatePayments(loanApplicationTerms, holidayDetailDTO, currency, scheduleParams, lastRestDate, detail);
                        if (updateLatePaymentMap) {
                            updateLatePaymentsToMap(loanApplicationTerms, holidayDetailDTO, currency, scheduleParams.getLatePaymentMap(),
                                    scheduledDueDate, scheduleParams.getInstallments(), true, lastRestDate);
                        }
                    } else if (scheduleParams.getLoanRepaymentScheduleTransactionProcessor() != null) {
                        LocalDate applicableDate = getNextRestScheduleDate(transactionDate.minusDays(1), loanApplicationTerms,
                                holidayDetailDTO);
                        if (applicableDate.isBefore(scheduledDueDate)) {
                            List<LoanTransaction> currentTransactions = createCurrentTransactionList(detail);
                            Money unprocessed = scheduleParams.getLoanRepaymentScheduleTransactionProcessor()
                                    .handleRepaymentSchedule(currentTransactions, currency, scheduleParams.getInstallments());
                            Money arrears = fetchArrears(loanApplicationTerms, currency, detail.getTransaction());
                            if (unprocessed.isGreaterThanZero()) {
                                updateMapWithAmount(scheduleParams.getPrincipalPortionMap(), unprocessed, applicableDate);
                                currentPeriodParams.plusEarlyPaidAmount(unprocessed);
                                if (!scheduleParams.getOutstandingBalance().isGreaterThan(unprocessed) && !loanApplicationTerms
                                        .getPreClosureInterestCalculationStrategy().calculateTillRestFrequencyEnabled()) {
                                    scheduleParams.getCompoundingDateVariations().put(periodStartDateApplicableForInterest,
                                            new TreeMap<>(scheduleParams.getCompoundingMap()));
                                    LocalDate calculateTill = transactionDate;
                                    PrincipalInterest principalInterestForThisPeriod = calculatePrincipalInterestComponentsForPeriod(
                                            this.paymentPeriodsInOneYearCalculator,
                                            currentPeriodParams.getInterestCalculationGraceOnRepaymentPeriodFraction(),
                                            scheduleParams.getTotalCumulativePrincipal().minus(scheduleParams.getReducePrincipal()),
                                            scheduleParams.getTotalCumulativeInterest(), totalInterestChargedForFullLoanTerm,
                                            scheduleParams.getTotalOutstandingInterestPaymentDueToGrace(),
                                            scheduleParams.getOutstandingBalanceAsPerRest(), loanApplicationTerms,
                                            scheduleParams.getPeriodNumber(), mc, mergeVariationsToMap(scheduleParams),
                                            scheduleParams.getCompoundingMap(), periodStartDateApplicableForInterest, calculateTill,
                                            interestRates);
                                    if (!principalInterestForThisPeriod.interest()
                                            .plus(principalInterestForThisPeriod.interestPaymentDueToGrace())
                                            .plus(scheduleParams.getOutstandingBalance()).isGreaterThan(unprocessed)) {
                                        currentPeriodParams.minusEarlyPaidAmount(unprocessed);
                                        updateMapWithAmount(scheduleParams.getPrincipalPortionMap(), unprocessed.negated(), applicableDate);
                                        LoanTransaction loanTransaction = LoanTransaction.repayment(null, unprocessed, null,
                                                transactionDate, null);
                                        RecalculationDetail recalculationDetail = new RecalculationDetail(transactionDate, loanTransaction);
                                        unprocessedTransactions.add(recalculationDetail);
                                        break;
                                    }
                                }
                                LoanTransaction loanTransaction = LoanTransaction.repayment(null, unprocessed, null, scheduledDueDate,
                                        null);
                                RecalculationDetail recalculationDetail = new RecalculationDetail(scheduledDueDate, loanTransaction);
                                unprocessedTransactions.add(recalculationDetail);
                                checkForOutstanding = false;
                                scheduleParams.reduceOutstandingBalance(unprocessed);
                                Money addToPrinciapal = Money.zero(currency);
                                if (scheduleParams.getOutstandingBalance().isLessThanZero()) {
                                    addToPrinciapal = addToPrinciapal.plus(scheduleParams.getOutstandingBalance());
                                    scheduleParams.setOutstandingBalance(Money.zero(currency));
                                    updateMapWithAmount(scheduleParams.getPrincipalPortionMap(), addToPrinciapal, applicableDate);
                                    currentPeriodParams.plusEarlyPaidAmount(addToPrinciapal);
                                }
                            }
                            if (arrears.isGreaterThanZero() && applicableDate.isBefore(lastRestDate)) {
                                handleLatePayments(loanApplicationTerms, holidayDetailDTO, currency, scheduleParams, lastRestDate, detail);
                            }
                        }
                    }
                }
            }
            applicableTransactions.addAll(unprocessedTransactions);
            if (checkForOutstanding && scheduleParams.getOutstandingBalance().isZero() && scheduleParams.getDisburseDetailMap().isEmpty()) {
                currentPeriodParams.setSkipCurrentLoop(true);
            }
        }
    }
    private void handleLatePayments(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            final MonetaryCurrency currency, LoanScheduleParams scheduleParams, LocalDate lastRestDate, RecalculationDetail detail) {
        updateLatePaidAmountsToPrincipalMap(detail.getTransaction(), loanApplicationTerms, currency, holidayDetailDTO, lastRestDate,
                scheduleParams);
    }
    private void updateAmountsBasedOnEarlyPayment(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            LoanScheduleParams scheduleParams, final LoanScheduleModelPeriod installment, RecalculationDetail detail, Money unprocessed,
            Money addToPrinciapal) {
        updatePrincipalPaidPortionToMap(loanApplicationTerms, holidayDetailDTO, scheduleParams.getPrincipalPortionMap(), installment,
                detail, unprocessed.plus(addToPrinciapal), scheduleParams.getInstallments());
        scheduleParams.addTotalRepaymentExpected(unprocessed.plus(addToPrinciapal));
        scheduleParams.addTotalCumulativePrincipal(unprocessed.plus(addToPrinciapal));
    }
    private void updateOutstandingBalanceAsPerRest(final LoanScheduleParams scheduleParams, final LocalDate scheduledDueDate) {
        scheduleParams.setOutstandingBalanceAsPerRest(updateBalanceForInterestCalculation(scheduleParams.getPrincipalPortionMap(),
                scheduledDueDate, scheduleParams.getOutstandingBalanceAsPerRest(), false));
        scheduleParams.setOutstandingBalanceAsPerRest(updateBalanceForInterestCalculation(scheduleParams.getDisburseDetailMap(),
                scheduledDueDate, scheduleParams.getOutstandingBalanceAsPerRest(), true));
    }
    private void updateBalanceBasedOnDisbursement(final LoanApplicationTerms loanApplicationTerms,
            final BigDecimal chargesDueAtTimeOfDisbursement, LoanScheduleParams scheduleParams,
            final Collection<LoanScheduleModelPeriod> periods, final LocalDate scheduledDueDate) {
        for (Map.Entry<LocalDate, Money> disburseDetail : scheduleParams.getDisburseDetailMap().entrySet()) {
            if (disburseDetail.getKey().isAfter(scheduleParams.getPeriodStartDate())
                    && !disburseDetail.getKey().isAfter(scheduledDueDate)) {
                if (loanApplicationTerms.getMaxOutstandingBalance() != null && scheduleParams.getOutstandingBalance()
                        .plus(disburseDetail.getValue()).isGreaterThan(loanApplicationTerms.getMaxOutstandingBalance())) {
                    String errorMsg = "Outstanding balance must not exceed the amount: " + loanApplicationTerms.getMaxOutstandingBalance();
                    throw new MultiDisbursementOutstandingAmoutException(errorMsg,
                            loanApplicationTerms.getMaxOutstandingBalance().getAmount(), disburseDetail.getValue());
                }
                final LoanScheduleModelDisbursementPeriod disbursementPeriod = LoanScheduleModelDisbursementPeriod
                        .disbursement(disburseDetail.getKey(), disburseDetail.getValue(), chargesDueAtTimeOfDisbursement);
                periods.add(disbursementPeriod);
                scheduleParams.addOutstandingBalance(disburseDetail.getValue());
                scheduleParams.addPrincipalToBeScheduled(disburseDetail.getValue());
                loanApplicationTerms.setPrincipal(loanApplicationTerms.getPrincipal().plus(disburseDetail.getValue()));
            }
        }
    }
    private LoanTermVariationParams applyLoanTermVariations(final LoanApplicationTerms loanApplicationTerms,
            final LoanScheduleParams scheduleParams, final LocalDate previousRepaymentDate, final LocalDate scheduledDueDate,
            Collection<LoanTermVariationsData> interestRatesForInstallments, PaymentPeriodsInOneYearCalculator calculator, MathContext mc) {
        boolean skipPeriod = false;
        boolean recalculateAmounts = false;
        LocalDate modifiedScheduledDueDate = scheduledDueDate;
        ArrayList<LoanTermVariationsData> variationsData = null;
        if (loanApplicationTerms.getLoanTermVariations().hasDueDateVariation(scheduledDueDate)) {
            LoanTermVariationsData loanTermVariationsData = loanApplicationTerms.getLoanTermVariations().nextDueDateVariation();
            if (loanTermVariationsData.getTermApplicableFrom().isEqual(modifiedScheduledDueDate)) {
                modifiedScheduledDueDate = loanTermVariationsData.getDateValue();
                if (!loanTermVariationsData.isSpecificToInstallment()) {
                    scheduleParams.setActualRepaymentDate(modifiedScheduledDueDate);
                    loanApplicationTerms.setNewScheduledDueDateStart(modifiedScheduledDueDate);
                }
                loanTermVariationsData.setProcessed(true);
            }
        }
        for (LoanTermVariationsData variation : interestRatesForInstallments) {
            if (variation.isApplicable(modifiedScheduledDueDate) && variation.getDecimalValue() != null && !variation.isProcessed()) {
                loanApplicationTerms.updateAnnualNominalInterestRate(variation.getDecimalValue());
                if (loanApplicationTerms.getInterestMethod().isDecliningBalnce()) {
                    if (loanApplicationTerms.getActualFixedEmiAmount() == null) {
                        loanApplicationTerms.setFixedEmiAmount(null);
                    }
                } else {
                    Money totalInterestDueForLoan = Money.zero(loanApplicationTerms.getCurrency());
                    loanApplicationTerms.setTotalPrincipalAccountedForInterestCalculation(scheduleParams.getTotalCumulativePrincipal());
                    totalInterestDueForLoan = loanApplicationTerms.calculateTotalInterestCharged(calculator, mc);
                    totalInterestDueForLoan = totalInterestDueForLoan.plus(scheduleParams.getTotalCumulativeInterest());
                    loanApplicationTerms.updateTotalInterestDue(totalInterestDueForLoan);
                    loanApplicationTerms.updateExcludePeriodsForCalculation(scheduleParams.getPeriodNumber() - 1);
                }
                variation.setProcessed(true);
            }
        }
        while (loanApplicationTerms.getLoanTermVariations().hasVariation(modifiedScheduledDueDate)) {
            LoanTermVariationsData loanTermVariationsData = loanApplicationTerms.getLoanTermVariations().nextVariation();
            if (loanTermVariationsData.isProcessed()) {
                continue;
            }
            switch (loanTermVariationsData.getTermVariationType()) {
                case INSERT_INSTALLMENT:
                    scheduleParams.setActualRepaymentDate(previousRepaymentDate);
                    modifiedScheduledDueDate = loanTermVariationsData.getTermApplicableFrom();
                    if (loanTermVariationsData.getDecimalValue() != null) {
                        if (loanApplicationTerms.getInterestMethod().isDecliningBalnce()
                                && loanApplicationTerms.getAmortizationMethod().isEqualInstallment()) {
                            loanApplicationTerms.setCurrentPeriodFixedEmiAmount(loanTermVariationsData.getDecimalValue());
                        } else {
                            loanApplicationTerms.setCurrentPeriodFixedPrincipalAmount(loanTermVariationsData.getDecimalValue());
                        }
                        recalculateAmounts = true;
                    }
                    loanTermVariationsData.setProcessed(true);
                break;
                case DELETE_INSTALLMENT:
                    if (loanTermVariationsData.getTermApplicableFrom().isEqual(modifiedScheduledDueDate)) {
                        skipPeriod = true;
                        loanTermVariationsData.setProcessed(true);
                    }
                break;
                case EMI_AMOUNT:
                    if (loanTermVariationsData.isSpecificToInstallment()) {
                        loanApplicationTerms.setCurrentPeriodFixedEmiAmount(loanTermVariationsData.getDecimalValue());
                        recalculateAmounts = true;
                    } else {
                        loanApplicationTerms.setFixedEmiAmount(loanTermVariationsData.getDecimalValue());
                    }
                    loanTermVariationsData.setProcessed(true);
                break;
                case PRINCIPAL_AMOUNT:
                    if (loanTermVariationsData.isSpecificToInstallment()) {
                        loanApplicationTerms.setCurrentPeriodFixedPrincipalAmount(loanTermVariationsData.getDecimalValue());
                        recalculateAmounts = true;
                    } else {
                        loanApplicationTerms.setFixedPrincipalAmount(loanTermVariationsData.getDecimalValue());
                    }
                    loanTermVariationsData.setProcessed(true);
                break;
                case EXTEND_REPAYMENT_PERIOD:
                    Integer rescheduleNumberOfRepayments = loanApplicationTerms.getNumberOfRepayments();
                    rescheduleNumberOfRepayments += loanTermVariationsData.getDecimalValue().intValue();
                    loanApplicationTerms.updateNumberOfRepayments(rescheduleNumberOfRepayments);
                    LocalDate loanEndDate = this.scheduledDateGenerator.getLastRepaymentDate(loanApplicationTerms,
                            loanApplicationTerms.getHolidayDetailDTO());
                    loanApplicationTerms.updateLoanEndDate(loanEndDate);
                    loanApplicationTerms.updateAccountedTillPeriod(scheduleParams.getPeriodNumber() - 1,
                            scheduleParams.getTotalCumulativePrincipal(), scheduleParams.getTotalCumulativeInterest(),
                            loanTermVariationsData.getDecimalValue().intValue());
                    adjustInstallmentOrPrincipalAmount(loanApplicationTerms, scheduleParams.getTotalCumulativePrincipal(),
                            scheduleParams.getPeriodNumber(), mc);
                    loanTermVariationsData.setProcessed(true);
                break;
                case GRACE_ON_PRINCIPAL:
                    loanApplicationTerms.updatePrincipalGrace(loanTermVariationsData.getDecimalValue().intValue());
                    Integer interestPaymentGrace = 0;
                    loanApplicationTerms.updateInterestPaymentGrace(interestPaymentGrace);
                    loanApplicationTerms.updatePeriodNumberApplicableForPrincipalOrInterestGrace(scheduleParams.getPeriodNumber());
                    loanTermVariationsData.setProcessed(true);
                break;
                case GRACE_ON_INTEREST:
                    loanApplicationTerms.updateInterestPaymentGrace(loanTermVariationsData.getDecimalValue().intValue());
                    Integer principalGrace = 0;
                    loanApplicationTerms.updatePrincipalGrace(principalGrace);
                    loanApplicationTerms.updatePeriodNumberApplicableForPrincipalOrInterestGrace(scheduleParams.getPeriodNumber());
                    loanApplicationTerms.updateTotalInterestAccounted(scheduleParams.getTotalCumulativeInterest());
                    loanTermVariationsData.setProcessed(true);
                break;
                default:
                break;
            }
        }
        LoanTermVariationParams termVariationParams = new LoanTermVariationParams(skipPeriod, recalculateAmounts, modifiedScheduledDueDate,
                variationsData);
        return termVariationParams;
    }
    private LoanTermVariationParams applyExceptionLoanTermVariations(final LoanApplicationTerms loanApplicationTerms,
            final LocalDate scheduledDueDate, final ListIterator<LoanTermVariationsData> exceptionDataListIterator, int instalmentNumber,
            Money totalCumulativePrincipal, Money totalCumulativeInterest, MathContext mc) {
        boolean skipPeriod = false;
        boolean recalculateAmounts = false;
        LocalDate modifiedScheduledDueDate = scheduledDueDate;
        ArrayList<LoanTermVariationsData> variationsData = new ArrayList<>();
        for (LoanTermVariationsData variation : loanApplicationTerms.getLoanTermVariations().getInterestRateFromInstallment()) {
            if (variation.isApplicable(modifiedScheduledDueDate) && variation.getDecimalValue() != null && !variation.isProcessed()) {
                loanApplicationTerms.updateAnnualNominalInterestRate(variation.getDecimalValue());
                if (loanApplicationTerms.getInterestMethod().isDecliningBalnce()) {
                    adjustInstallmentOrPrincipalAmount(loanApplicationTerms, totalCumulativePrincipal, instalmentNumber, mc);
                } else {
                    loanApplicationTerms.setTotalPrincipalAccountedForInterestCalculation(totalCumulativePrincipal);
                    loanApplicationTerms.updateExcludePeriodsForCalculation(instalmentNumber - 1);
                }
                variation.setProcessed(true);
            }
        }
        while (loanApplicationTerms.getLoanTermVariations().hasExceptionVariation(modifiedScheduledDueDate, exceptionDataListIterator)) {
            LoanTermVariationsData loanTermVariationsData = exceptionDataListIterator.next();
            if (loanTermVariationsData.isProcessed()) {
                continue;
            }
            switch (loanTermVariationsData.getTermVariationType()) {
                case INSERT_INSTALLMENT:
                    modifiedScheduledDueDate = loanTermVariationsData.getTermApplicableFrom();
                    variationsData.add(loanTermVariationsData);
                break;
                case DELETE_INSTALLMENT:
                    if (loanTermVariationsData.getTermApplicableFrom().isEqual(modifiedScheduledDueDate)) {
                        skipPeriod = true;
                        variationsData.add(loanTermVariationsData);
                    }
                break;
                case GRACE_ON_PRINCIPAL:
                    loanApplicationTerms.updatePrincipalGrace(loanTermVariationsData.getDecimalValue().intValue());
                    Integer interestPaymentGrace = 0;
                    loanApplicationTerms.updateInterestPaymentGrace(interestPaymentGrace);
                    loanApplicationTerms.updatePeriodNumberApplicableForPrincipalOrInterestGrace(instalmentNumber);
                    variationsData.add(loanTermVariationsData);
                break;
                case GRACE_ON_INTEREST:
                    loanApplicationTerms.updateInterestPaymentGrace(loanTermVariationsData.getDecimalValue().intValue());
                    Integer principalGrace = 0;
                    loanApplicationTerms.updatePrincipalGrace(principalGrace);
                    loanApplicationTerms.updatePeriodNumberApplicableForPrincipalOrInterestGrace(instalmentNumber);
                    loanApplicationTerms.updateTotalInterestAccounted(totalCumulativeInterest);
                    variationsData.add(loanTermVariationsData);
                break;
                case EXTEND_REPAYMENT_PERIOD:
                    Integer rescheduleNumberOfRepayments = loanApplicationTerms.getNumberOfRepayments();
                    rescheduleNumberOfRepayments += loanTermVariationsData.getDecimalValue().intValue();
                    loanApplicationTerms.updateNumberOfRepayments(rescheduleNumberOfRepayments);
                    LocalDate loanEndDate = this.scheduledDateGenerator.getLastRepaymentDate(loanApplicationTerms,
                            loanApplicationTerms.getHolidayDetailDTO());
                    loanApplicationTerms.updateLoanEndDate(loanEndDate);
                    adjustInstallmentOrPrincipalAmount(loanApplicationTerms, totalCumulativePrincipal, instalmentNumber, mc);
                    loanTermVariationsData.setProcessed(true);
                    loanApplicationTerms.updateAccountedTillPeriod(instalmentNumber - 1, totalCumulativePrincipal, totalCumulativeInterest,
                            loanTermVariationsData.getDecimalValue().intValue());
                break;
                default:
                break;
            }
        }
        LoanTermVariationParams termVariationParams = new LoanTermVariationParams(skipPeriod, recalculateAmounts, modifiedScheduledDueDate,
                variationsData);
        return termVariationParams;
    }
    private void applyLoanVariationsForPartialScheduleGenerate(final LoanApplicationTerms loanApplicationTerms,
            LoanScheduleParams scheduleParams, final Collection<LoanTermVariationsData> interestRates,
            final Collection<LoanTermVariationsData> interestRatesForInstallments) {
        while (loanApplicationTerms.getLoanTermVariations().hasVariation(scheduleParams.getPeriodStartDate())) {
            LoanTermVariationsData variation = loanApplicationTerms.getLoanTermVariations().nextVariation();
            if (!variation.isSpecificToInstallment()) {
                switch (variation.getTermVariationType()) {
                    case EMI_AMOUNT:
                        loanApplicationTerms.setFixedEmiAmount(variation.getDecimalValue());
                    break;
                    case PRINCIPAL_AMOUNT:
                        loanApplicationTerms.setFixedPrincipalAmount(variation.getDecimalValue());
                    break;
                    default:
                    break;
                }
            }
            variation.setProcessed(true);
        }
        for (LoanTermVariationsData variation : interestRates) {
            if (variation.getTermVariationType().isInterestRateVariation() && variation.isApplicable(scheduleParams.getPeriodStartDate())
                    && variation.getDecimalValue() != null) {
                loanApplicationTerms.updateAnnualNominalInterestRate(variation.getDecimalValue());
            }
        }
        for (LoanTermVariationsData variation : interestRatesForInstallments) {
            if (variation.getTermVariationType().isInterestRateFromInstallment()
                    && variation.isApplicable(scheduleParams.getPeriodStartDate()) && variation.getDecimalValue() != null) {
                loanApplicationTerms.updateAnnualNominalInterestRate(variation.getDecimalValue());
                variation.setProcessed(true);
            }
        }
    }
    private Money getPrincipalToBeScheduled(final LoanApplicationTerms loanApplicationTerms) {
        Money principalToBeScheduled;
        if (loanApplicationTerms.isMultiDisburseLoan() && loanApplicationTerms.getApprovedPrincipal().isGreaterThanZero()) {
            principalToBeScheduled = loanApplicationTerms.getApprovedPrincipal();
        } else {
            principalToBeScheduled = loanApplicationTerms.getPrincipal();
        }
        return principalToBeScheduled;
    }
    private boolean updateFixedInstallmentAmount(final MathContext mc, final LoanApplicationTerms loanApplicationTerms, int periodNumber,
            Money outstandingBalance) {
        boolean isAmountChanged = false;
        if (loanApplicationTerms.getActualFixedEmiAmount() == null && loanApplicationTerms.getInterestMethod().isDecliningBalnce()
                && loanApplicationTerms.getAmortizationMethod().isEqualInstallment()) {
            if (periodNumber < loanApplicationTerms.getPrincipalGrace() + 1) {
                periodNumber = loanApplicationTerms.getPrincipalGrace() + 1;
            }
            Money emiAmount = loanApplicationTerms.pmtForInstallment(this.paymentPeriodsInOneYearCalculator, outstandingBalance,
                    periodNumber, mc);
            loanApplicationTerms.setFixedEmiAmount(emiAmount.getAmount());
            isAmountChanged = true;
        }
        return isAmountChanged;
    }
    private Money fetchArrears(final LoanApplicationTerms loanApplicationTerms, final MonetaryCurrency currency,
            final LoanTransaction transaction) {
        Money arrears = transaction.getPrincipalPortion(currency);
        arrears = arrears.plus(fetchCompoundedArrears(loanApplicationTerms, currency, transaction));
        return arrears;
    }
    private Money fetchCompoundedArrears(final LoanApplicationTerms loanApplicationTerms, final MonetaryCurrency currency,
            final LoanTransaction transaction) {
        Money arrears = Money.zero(currency);
        if (loanApplicationTerms.getInterestRecalculationCompoundingMethod().isInterestCompoundingEnabled()) {
            arrears = arrears.plus(transaction.getInterestPortion(currency));
        }
        if (loanApplicationTerms.getInterestRecalculationCompoundingMethod().isFeeCompoundingEnabled()) {
            arrears = arrears.plus(transaction.getFeeChargesPortion(currency)).plus(transaction.getPenaltyChargesPortion(currency));
        }
        return arrears;
    }
    private Money addInterestOnlyRepaymentScheduleForCurrentdate(final MathContext mc, final LoanApplicationTerms loanApplicationTerms,
            final HolidayDetailDTO holidayDetailDTO, final MonetaryCurrency currency, final Collection<LoanScheduleModelPeriod> periods,
            final LocalDate currentDate, LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor,
            final Collection<RecalculationDetail> transactions, final Set<LoanCharge> loanCharges, final LoanScheduleParams params) {
        boolean isFirstRepayment = false;
        LocalDate startDate = params.getPeriodStartDate();
        Money outstanding = params.getOutstandingBalanceAsPerRest();
        Money totalInterest = Money.zero(currency);
        Money totalCumulativeInterest = Money.zero(currency);
        double interestCalculationGraceOnRepaymentPeriodFraction = Double.valueOf(0);
        int periodNumberTemp = 1;
        LocalDate lastRestDate = getNextRestScheduleDate(currentDate.minusDays(1), loanApplicationTerms, holidayDetailDTO);
        Collection<LoanTermVariationsData> applicableVariations = loanApplicationTerms.getLoanTermVariations().getInterestRateChanges();
        Money uncompoundedFromLastInstallment = params.getUnCompoundedAmount();
        LocalDate additionalPeriodsStartDate = params.getPeriodStartDate();
        do {
            params.setActualRepaymentDate(this.scheduledDateGenerator.generateNextRepaymentDate(params.getActualRepaymentDate(),
                    loanApplicationTerms, isFirstRepayment));
            if (params.getActualRepaymentDate().isAfter(currentDate)) {
                params.setActualRepaymentDate(currentDate);
            }
            Collection<RecalculationDetail> applicableTransactions = getApplicableTransactionsForPeriod(params.applyInterestRecalculation(),
                    params.getActualRepaymentDate(), transactions);
            populateCompoundingDatesInPeriod(params.getPeriodStartDate(), params.getActualRepaymentDate(), loanApplicationTerms,
                    holidayDetailDTO, params, loanCharges, currency);
            for (RecalculationDetail detail : applicableTransactions) {
                if (detail.isProcessed()) {
                    continue;
                }
                LocalDate transactionDate = detail.getTransactionDate();
                List<LoanTransaction> currentTransactions = createCurrentTransactionList(detail);
                if (!params.getPeriodStartDate().isEqual(transactionDate)) {
                    PrincipalInterest principalInterestForThisPeriod = calculatePrincipalInterestComponentsForPeriod(
                            this.paymentPeriodsInOneYearCalculator, interestCalculationGraceOnRepaymentPeriodFraction, totalInterest.zero(),
                            totalInterest.zero(), totalInterest.zero(), totalInterest.zero(), outstanding, loanApplicationTerms,
                            periodNumberTemp, mc, mergeVariationsToMap(params), params.getCompoundingMap(), params.getPeriodStartDate(),
                            transactionDate, applicableVariations);
                    Money interest = principalInterestForThisPeriod.interest();
                    totalInterest = totalInterest.plus(interest);
                    LoanScheduleModelRepaymentPeriod installment = LoanScheduleModelRepaymentPeriod.repayment(params.getInstalmentNumber(),
                            startDate, transactionDate, totalInterest.zero(), totalInterest.zero(), totalInterest, totalInterest.zero(),
                            totalInterest.zero(), totalInterest, true);
                    params.incrementInstalmentNumber();
                    periods.add(installment);
                    totalCumulativeInterest = totalCumulativeInterest.plus(totalInterest);
                    totalInterest = totalInterest.zero();
                    addLoanRepaymentScheduleInstallment(params.getInstallments(), installment);
                    updateCompoundingMap(loanApplicationTerms, holidayDetailDTO, params, lastRestDate, transactionDate);
                    populateCompoundingDatesInPeriod(installment.periodDueDate(), params.getActualRepaymentDate(), loanApplicationTerms,
                            holidayDetailDTO, params, loanCharges, currency);
                    uncompoundedFromLastInstallment = params.getUnCompoundedAmount();
                    params.setPeriodStartDate(transactionDate);
                    startDate = transactionDate;
                    additionalPeriodsStartDate = startDate;
                }
                loanRepaymentScheduleTransactionProcessor.handleRepaymentSchedule(currentTransactions, currency, params.getInstallments());
                updateLatePaidAmountsToPrincipalMap(detail.getTransaction(), loanApplicationTerms, currency, holidayDetailDTO, lastRestDate,
                        params);
                updateLatePaymentsToMap(loanApplicationTerms, holidayDetailDTO, currency, params.getLatePaymentMap(), currentDate,
                        params.getInstallments(), false, lastRestDate);
                if (params.getLatePaymentMap().isEmpty() && isCompleted(params.getInstallments())) {
                    outstanding = outstanding.zero();
                } else {
                    outstanding = updateBalanceForInterestCalculation(params.getPrincipalPortionMap(), params.getPeriodStartDate(),
                            outstanding, false);
                }
                if (params.getLatePaymentMap().isEmpty() && outstanding.isZero()) {
                    break;
                }
            }
            if (!outstanding.isZero()) {
                PrincipalInterest principalInterestForThisPeriod = calculatePrincipalInterestComponentsForPeriod(
                        this.paymentPeriodsInOneYearCalculator, interestCalculationGraceOnRepaymentPeriodFraction, totalInterest.zero(),
                        totalInterest.zero(), totalInterest.zero(), totalInterest.zero(), outstanding, loanApplicationTerms,
                        periodNumberTemp, mc, mergeVariationsToMap(params), params.getCompoundingMap(), params.getPeriodStartDate(),
                        params.getActualRepaymentDate(), applicableVariations);
                Money interest = principalInterestForThisPeriod.interest();
                totalInterest = totalInterest.plus(interest);
                if (loanApplicationTerms.getInterestRecalculationCompoundingMethod().isCompoundingEnabled()) {
                    Money uncompounded = params.getUnCompoundedAmount();
                    Money compounded = uncompounded.zero();
                    for (Map.Entry<LocalDate, Money> mapEntry : params.getCompoundingMap().entrySet()) {
                        if (mapEntry.getKey().isAfter(params.getPeriodStartDate())) {
                            compounded = compounded.plus(mapEntry.getValue());
                        }
                    }
                    if (compounded.isGreaterThanZero() && startDate.isEqual(additionalPeriodsStartDate)) {
                        params.setCompoundedInLastInstallment(uncompoundedFromLastInstallment);
                        additionalPeriodsStartDate = additionalPeriodsStartDate.plusDays(1);
                    }
                    Money compoundedForThisPeriod = compounded.minus(uncompounded);
                    Money uncompoundedForThisPeriod = interest.minus(compoundedForThisPeriod);
                    params.setUnCompoundedAmount(uncompoundedForThisPeriod);
                    LocalDate compoundingDate = params.getPeriodStartDate();
                    if (loanApplicationTerms.allowCompoundingOnEod()) {
                        compoundingDate = compoundingDate.minusDays(1);
                    }
                    compoundingDate = getNextCompoundScheduleDate(compoundingDate, loanApplicationTerms, holidayDetailDTO);
                    if (compoundingDate.isEqual(params.getActualRepaymentDate())) {
                        params.getCompoundingMap().put(compoundingDate, uncompoundedForThisPeriod);
                        params.setUnCompoundedAmount(uncompoundedForThisPeriod.zero());
                    }
                }
            }
            params.setPeriodStartDate(params.getActualRepaymentDate());
        } while (params.getActualRepaymentDate().isBefore(currentDate) && !outstanding.isZero());
        if (totalInterest.isGreaterThanZero()) {
            LoanScheduleModelRepaymentPeriod installment = LoanScheduleModelRepaymentPeriod.repayment(params.getInstalmentNumber(),
                    startDate, params.getActualRepaymentDate(), totalInterest.zero(), totalInterest.zero(), totalInterest,
                    totalInterest.zero(), totalInterest.zero(), totalInterest, true);
            params.incrementInstalmentNumber();
            periods.add(installment);
            params.getCompoundingDateVariations().put(startDate, new TreeMap<>(params.getCompoundingMap()));
            totalCumulativeInterest = totalCumulativeInterest.plus(totalInterest);
        }
        return totalCumulativeInterest;
    }
    private boolean isCompleted(List<LoanRepaymentScheduleInstallment> installments) {
        boolean isCompleted = true;
        for (LoanRepaymentScheduleInstallment installment : installments) {
            if (installment.isNotFullyPaidOff()) {
                isCompleted = false;
                break;
            }
        }
        return isCompleted;
    }
    private Collection<RecalculationDetail> getApplicableTransactionsForPeriod(final boolean applyInterestRecalculation,
            LocalDate repaymentDate, final Collection<RecalculationDetail> transactions) {
        Collection<RecalculationDetail> applicableTransactions = new ArrayList<>();
        if (applyInterestRecalculation) {
            for (RecalculationDetail detail : transactions) {
                if (!detail.getTransactionDate().isAfter(repaymentDate)) {
                    applicableTransactions.add(detail);
                }
            }
            transactions.removeAll(applicableTransactions);
        }
        return applicableTransactions;
    }
    private List<LoanTransaction> createCurrentTransactionList(RecalculationDetail detail) {
        List<LoanTransaction> currentTransactions = new ArrayList<>(2);
        currentTransactions.add(detail.getTransaction());
        detail.setProcessed(true);
        return currentTransactions;
    }
    private Money applyEarlyPaymentStrategy(final LoanApplicationTerms loanApplicationTerms, Money reducePrincipal,
            final Money totalCumulativePrincipal, int periodNumber, final MathContext mc) {
        if (reducePrincipal.isGreaterThanZero()) {
            switch (loanApplicationTerms.getRescheduleStrategyMethod()) {
                case REDUCE_EMI_AMOUNT:
                    adjustInstallmentOrPrincipalAmount(loanApplicationTerms, totalCumulativePrincipal, periodNumber, mc);
                    reducePrincipal = reducePrincipal.zero();
                break;
                case REDUCE_NUMBER_OF_INSTALLMENTS:
                    reducePrincipal = reducePrincipal.zero();
                break;
                case RESCHEDULE_NEXT_REPAYMENTS:
                break;
                default:
                break;
            }
        }
        return reducePrincipal;
    }
    private void adjustInstallmentOrPrincipalAmount(final LoanApplicationTerms loanApplicationTerms, final Money totalCumulativePrincipal,
            int periodNumber, final MathContext mc) {
        Money principal = getPrincipalToBeScheduled(loanApplicationTerms);
        if (!principal.minus(totalCumulativePrincipal).isGreaterThanZero()) {
            return;
        }
        if (loanApplicationTerms.getAmortizationMethod().isEqualPrincipal()) {
            loanApplicationTerms.updateFixedPrincipalAmount(mc, periodNumber, principal.minus(totalCumulativePrincipal));
        } else if (loanApplicationTerms.getActualFixedEmiAmount() == null) {
            loanApplicationTerms.setFixedEmiAmount(null);
            updateFixedInstallmentAmount(mc, loanApplicationTerms, periodNumber, principal.minus(totalCumulativePrincipal));
        }
    }
    private Money updateBalanceForInterestCalculation(final Map<LocalDate, Money> principalPortionMap, final LocalDate scheduledDueDate,
            final Money outstandingBalanceAsPerRest, boolean addMapDetails) {
        List<LocalDate> removeFromprincipalPortionMap = new ArrayList<>();
        Money outstandingBalance = outstandingBalanceAsPerRest;
        for (Map.Entry<LocalDate, Money> principal : principalPortionMap.entrySet()) {
            if (!principal.getKey().isAfter(scheduledDueDate)) {
                if (addMapDetails) {
                    outstandingBalance = outstandingBalance.plus(principal.getValue());
                } else {
                    outstandingBalance = outstandingBalance.minus(principal.getValue());
                }
                removeFromprincipalPortionMap.add(principal.getKey());
            }
        }
        for (LocalDate date : removeFromprincipalPortionMap) {
            principalPortionMap.remove(date);
        }
        return outstandingBalance;
    }
    private void updateLatePaidAmountsToPrincipalMap(final LoanTransaction loanTransaction, final LoanApplicationTerms applicationTerms,
            final MonetaryCurrency currency, final HolidayDetailDTO holidayDetailDTO, final LocalDate lastRestDate,
            final LoanScheduleParams params) {
        LocalDate applicableDate = getNextRestScheduleDate(loanTransaction.getTransactionDate().minusDays(1), applicationTerms,
                holidayDetailDTO);
        Money principalPortion = loanTransaction.getPrincipalPortion(currency);
        updateLatePaymentCompoundingAmount(params.getPrincipalPortionMap(), params.getLatePaymentMap(), currency, lastRestDate,
                principalPortion, applicableDate);
        adjustCompoundedAmountWithPaidDetail(params, lastRestDate, applicableDate, loanTransaction, applicationTerms);
    }
    private void updateLatePaymentCompoundingAmount(final Map<LocalDate, Money> principalVariationMap,
            final Map<LocalDate, Money> latePaymentCompoundingMap, final MonetaryCurrency currency, final LocalDate lastRestDate,
            Money compoundedPortion, final LocalDate applicableDate) {
        Money appliedOnPrincipalVariationMap = Money.zero(currency);
        Map<LocalDate, Money> temp = new HashMap<>();
        for (LocalDate date : latePaymentCompoundingMap.keySet()) {
            if (date.isBefore(lastRestDate)) {
                Money money = latePaymentCompoundingMap.get(date);
                appliedOnPrincipalVariationMap = appliedOnPrincipalVariationMap.plus(money);
                if (appliedOnPrincipalVariationMap.isLessThan(compoundedPortion)) {
                    if (date.isBefore(applicableDate)) {
                        updateMapWithAmount(principalVariationMap, money.negated(), date);
                        updateMapWithAmount(principalVariationMap, money, applicableDate);
                    }
                } else if (temp.isEmpty()) {
                    Money diff = money.minus(appliedOnPrincipalVariationMap.minus(compoundedPortion));
                    updateMapWithAmount(principalVariationMap, diff.negated(), date);
                    updateMapWithAmount(principalVariationMap, diff, applicableDate);
                    updateMapWithAmount(temp, money.minus(diff), date);
                    updateMapWithAmount(temp, money.minus(diff).negated(), lastRestDate);
                } else {
                    updateMapWithAmount(temp, money, date);
                    updateMapWithAmount(temp, money.negated(), lastRestDate);
                }
            }
        }
        latePaymentCompoundingMap.clear();
        latePaymentCompoundingMap.putAll(temp);
    }
    private void updateLatePaymentsToMap(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            final MonetaryCurrency currency, final Map<LocalDate, Money> latePaymentMap, final LocalDate scheduledDueDate,
            List<LoanRepaymentScheduleInstallment> installments, boolean applyRestFrequencyForPrincipal, final LocalDate lastRestDate) {
        latePaymentMap.clear();
        LocalDate currentDate = DateUtils.getBusinessLocalDate();
        Money totalCompoundingAmount = Money.zero(currency);
        for (LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment : installments) {
            if (loanRepaymentScheduleInstallment.isNotFullyPaidOff()
                    && !loanRepaymentScheduleInstallment.getDueDate().isAfter(scheduledDueDate)
                    && !loanRepaymentScheduleInstallment.isRecalculatedInterestComponent()) {
                LocalDate principalEffectiveDate = loanRepaymentScheduleInstallment.getDueDate();
                if (applyRestFrequencyForPrincipal) {
                    principalEffectiveDate = getNextRestScheduleDate(loanRepaymentScheduleInstallment.getDueDate().minusDays(1),
                            loanApplicationTerms, holidayDetailDTO);
                }
                if (principalEffectiveDate.isBefore(currentDate)) {
                    updateMapWithAmount(latePaymentMap, loanRepaymentScheduleInstallment.getPrincipalOutstanding(currency),
                            principalEffectiveDate);
                    totalCompoundingAmount = totalCompoundingAmount
                            .plus(loanRepaymentScheduleInstallment.getPrincipalOutstanding(currency));
                }
            }
        }
        if (totalCompoundingAmount.isGreaterThanZero()) {
            updateMapWithAmount(latePaymentMap, totalCompoundingAmount.negated(), lastRestDate);
        }
    }
    private void updateCompoundingMap(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            final LoanScheduleParams params, final LocalDate lastRestDate, final LocalDate scheduledDueDate) {
        if (loanApplicationTerms.isInterestRecalculationEnabled()
                && loanApplicationTerms.getInterestRecalculationCompoundingMethod().isCompoundingEnabled()) {
            final MonetaryCurrency currency = params.getCurrency();
            Money totalCompoundedAmount = Money.zero(currency);
            boolean lastInstallmentIsPastDate = false;
            for (LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment : params.getInstallments()) {
                if (params.getCompoundingDateVariations().containsKey(loanRepaymentScheduleInstallment.getFromDate())) {
                    lastInstallmentIsPastDate = params.applyInterestRecalculation()
                            && loanRepaymentScheduleInstallment.getDueDate().isBefore(DateUtils.getBusinessLocalDate());
                } else {
                    final boolean isPastDate = params.applyInterestRecalculation()
                            && loanRepaymentScheduleInstallment.getDueDate().isBefore(DateUtils.getBusinessLocalDate());
                    boolean periodHasCompoundingDate = false;
                    Money amountCharged = Money.zero(currency);
                    if (loanApplicationTerms.getInterestRecalculationCompoundingMethod() != null) {
                        amountCharged = getIncomeForCompounding(loanApplicationTerms, currency, loanRepaymentScheduleInstallment);
                    }
                    final Map<LocalDate, Money> compoundingMap = params.getCompoundingMap();
                    LocalDate effectiveStartDate = loanRepaymentScheduleInstallment.getFromDate();
                    if (loanApplicationTerms.allowCompoundingOnEod()) {
                        effectiveStartDate = loanRepaymentScheduleInstallment.getFromDate().minusDays(1);
                    }
                    LocalDate compoundingEffectiveDate = getNextCompoundScheduleDate(effectiveStartDate, loanApplicationTerms,
                            holidayDetailDTO);
                    final LocalDate restDate = getNextRestScheduleDate(scheduledDueDate.minusDays(1), loanApplicationTerms,
                            holidayDetailDTO);
                    if (!compoundingEffectiveDate.isAfter(loanRepaymentScheduleInstallment.getDueDate())) {
                        Money amountCompoundedFromLastPeriod = params.getCompoundedInLastInstallment();
                        if (amountCompoundedFromLastPeriod.isZero()) {
                            amountCompoundedFromLastPeriod = params.getUnCompoundedAmount();
                        }
                        totalCompoundedAmount = totalCompoundedAmount.minus(amountCompoundedFromLastPeriod);
                        periodHasCompoundingDate = true;
                    }
                    while (!compoundingEffectiveDate.isAfter(loanRepaymentScheduleInstallment.getDueDate())) {
                        if (compoundingEffectiveDate.isEqual(loanRepaymentScheduleInstallment.getDueDate())) {
                            Money amountToBeCompounding = amountCharged.minus(totalCompoundedAmount);
                            updateMapWithAmount(compoundingMap, amountToBeCompounding, compoundingEffectiveDate);
                            totalCompoundedAmount = totalCompoundedAmount.plus(amountToBeCompounding);
                        } else if (compoundingMap.containsKey(compoundingEffectiveDate)) {
                            Money compounedAmount = compoundingMap.get(compoundingEffectiveDate);
                            totalCompoundedAmount = totalCompoundedAmount.plus(compounedAmount);
                        }
                        if (!loanApplicationTerms.allowCompoundingOnEod()) {
                            compoundingEffectiveDate = compoundingEffectiveDate.plusDays(1);
                        }
                        compoundingEffectiveDate = getNextCompoundScheduleDate(compoundingEffectiveDate, loanApplicationTerms,
                                holidayDetailDTO);
                    }
                    if (periodHasCompoundingDate) {
                        if (isPastDate) {
                            updateMapWithAmount(params.getPrincipalPortionMap(), totalCompoundedAmount.plus(params.getUnCompoundedAmount()),
                                    lastRestDate);
                        } else {
                            Money amountToBeEffected = amountCharged;
                            if (lastInstallmentIsPastDate) {
                                amountToBeEffected = amountToBeEffected.plus(params.getUnCompoundedAmount());
                            }
                            updateMapWithAmount(params.getPrincipalPortionMap(), amountToBeEffected, restDate);
                        }
                    }
                    if (totalCompoundedAmount.isGreaterThanZero()) {
                        params.getCompoundingDateVariations().put(loanRepaymentScheduleInstallment.getFromDate(),
                                new TreeMap<>(params.getCompoundingMap()));
                        for (Map.Entry<LocalDate, Money> mapEntry : params.getCompoundingMap().entrySet()) {
                            if (!mapEntry.getKey().isAfter(loanRepaymentScheduleInstallment.getDueDate())) {
                                updateMapWithAmount(params.getPrincipalPortionMap(), mapEntry.getValue().negated(), mapEntry.getKey());
                            }
                        }
                        params.minusUnCompoundedAmount(params.getUnCompoundedAmount());
                        params.getCompoundingMap().clear();
                        params.addUnCompoundedAmount(amountCharged.minus(totalCompoundedAmount));
                    } else {
                        params.getCompoundingMap().clear();
                        params.getCompoundingDateVariations().put(loanRepaymentScheduleInstallment.getFromDate(),
                                new TreeMap<>(params.getCompoundingMap()));
                        params.addUnCompoundedAmount(amountCharged);
                    }
                    params.setCompoundedInLastInstallment(amountCharged.zero());
                    lastInstallmentIsPastDate = isPastDate;
                }
            }
        }
    }
    private Money getIncomeForCompounding(final LoanApplicationTerms loanApplicationTerms, final MonetaryCurrency currency,
            LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        Money interestCharged = Money.zero(currency);
        Money feeCharged = Money.zero(currency);
        Money penaltyCharged = Money.zero(currency);
        Money amountCharged = Money.zero(currency);
        switch (loanApplicationTerms.getInterestRecalculationCompoundingMethod()) {
            case INTEREST:
                interestCharged = interestCharged.plus(loanRepaymentScheduleInstallment.getInterestCharged(currency));
            break;
            case FEE:
                feeCharged = feeCharged.plus(loanRepaymentScheduleInstallment.getFeeChargesCharged(currency));
                penaltyCharged = penaltyCharged.plus(loanRepaymentScheduleInstallment.getPenaltyChargesCharged(currency));
            break;
            case INTEREST_AND_FEE:
                interestCharged = interestCharged.plus(loanRepaymentScheduleInstallment.getInterestCharged(currency));
                feeCharged = feeCharged.plus(loanRepaymentScheduleInstallment.getFeeChargesCharged(currency));
                penaltyCharged = penaltyCharged.plus(loanRepaymentScheduleInstallment.getPenaltyChargesCharged(currency));
            break;
            default:
            break;
        }
        amountCharged = interestCharged.plus(feeCharged).plus(penaltyCharged);
        return amountCharged;
    }
    private void adjustCompoundedAmountWithPaidDetail(final LoanScheduleParams params, final LocalDate lastRestDate,
            final Collection<LoanTransaction> transactions, final LoanApplicationTerms loanApplicationTerms,
            HolidayDetailDTO holidayDetailDTO) {
        for (LoanTransaction loanTransaction : transactions) {
            final LocalDate amountApplicableDate = getNextRestScheduleDate(loanTransaction.getTransactionDate().minusDays(1),
                    loanApplicationTerms, holidayDetailDTO);
            adjustCompoundedAmountWithPaidDetail(params, lastRestDate, amountApplicableDate, loanTransaction, loanApplicationTerms);
        }
    }
    private void adjustCompoundedAmountWithPaidDetail(final LoanScheduleParams params, final LocalDate lastRestDate,
            final LocalDate amountApplicableDate, final LoanTransaction transaction, final LoanApplicationTerms loanApplicationTerms) {
        adjustCompoundedAmountWithPaidDetail(params.getPrincipalPortionMap(), lastRestDate, amountApplicableDate, transaction,
                loanApplicationTerms, params.getCurrency());
    }
    private void adjustCompoundedAmountWithPaidDetail(final Map<LocalDate, Money> principalPortionMap, final LocalDate lastRestDate,
            final LocalDate amountApplicableDate, final LoanTransaction transaction, final LoanApplicationTerms loanApplicationTerms,
            final MonetaryCurrency currency) {
        if (!amountApplicableDate.isEqual(lastRestDate)) {
            Money compoundedIncome = fetchCompoundedArrears(loanApplicationTerms, currency, transaction);
            updateMapWithAmount(principalPortionMap, compoundedIncome, amountApplicableDate);
            updateMapWithAmount(principalPortionMap, compoundedIncome.negated(), lastRestDate);
        }
    }
    private void populateCompoundingDatesInPeriod(final LocalDate startDate, final LocalDate endDate,
            final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            final LoanScheduleParams scheduleParams, final Set<LoanCharge> charges, MonetaryCurrency currency) {
        if (loanApplicationTerms.getInterestRecalculationCompoundingMethod().isCompoundingEnabled()) {
            final Map<LocalDate, Money> compoundingMap = scheduleParams.getCompoundingMap();
            LocalDate lastCompoundingDate = startDate;
            LocalDate compoundingDate = startDate;
            boolean addUncompounded = true;
            while (compoundingDate.isBefore(endDate)) {
                if (loanApplicationTerms.allowCompoundingOnEod()) {
                    compoundingDate = compoundingDate.minusDays(1);
                }
                compoundingDate = getNextCompoundScheduleDate(compoundingDate, loanApplicationTerms, holidayDetailDTO);
                if (compoundingDate.isBefore(endDate)) {
                    Money feeChargesForInstallment = cumulativeFeeChargesDueWithin(lastCompoundingDate, compoundingDate, charges, currency,
                            null, loanApplicationTerms.getPrincipal(), null, false);
                    Money penaltyChargesForInstallment = cumulativePenaltyChargesDueWithin(lastCompoundingDate, compoundingDate, charges,
                            currency, null, loanApplicationTerms.getPrincipal(), null, false);
                    Money compoundAmount = feeChargesForInstallment.plus(penaltyChargesForInstallment);
                    if (addUncompounded) {
                        compoundAmount = compoundAmount.plus(scheduleParams.getUnCompoundedAmount());
                        addUncompounded = false;
                    }
                    updateMapWithAmount(compoundingMap, compoundAmount, compoundingDate);
                }
                lastCompoundingDate = compoundingDate;
            }
        }
    }
    private void updatePrincipalPaidPortionToMap(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            Map<LocalDate, Money> principalPortionMap, final LoanScheduleModelPeriod installment, final RecalculationDetail detail,
            final Money unprocessed, final List<LoanRepaymentScheduleInstallment> installments) {
        LocalDate applicableDate = getNextRestScheduleDate(detail.getTransactionDate().minusDays(1), loanApplicationTerms,
                holidayDetailDTO);
        updateMapWithAmount(principalPortionMap, unprocessed, applicableDate);
        installment.addPrincipalAmount(unprocessed);
        LoanRepaymentScheduleInstallment lastInstallment = installments.get(installments.size() - 1);
        lastInstallment.updatePrincipal(lastInstallment.getPrincipal(unprocessed.getCurrency()).plus(unprocessed).getAmount());
        lastInstallment.payPrincipalComponent(detail.getTransactionDate(), unprocessed);
    }
    private TreeMap<LocalDate, Money> mergeVariationsToMap(final LoanScheduleParams params) {
        TreeMap<LocalDate, Money> map = new TreeMap<>();
        map.putAll(params.getLatePaymentMap());
        for (Map.Entry<LocalDate, Money> mapEntry : params.getDisburseDetailMap().entrySet()) {
            Money value = mapEntry.getValue();
            if (map.containsKey(mapEntry.getKey())) {
                value = value.plus(map.get(mapEntry.getKey()));
            }
            map.put(mapEntry.getKey(), value);
        }
        for (Map.Entry<LocalDate, Money> mapEntry : params.getPrincipalPortionMap().entrySet()) {
            Money value = mapEntry.getValue().negated();
            if (map.containsKey(mapEntry.getKey())) {
                value = value.plus(map.get(mapEntry.getKey()));
            }
            map.put(mapEntry.getKey(), value);
        }
        for (Map.Entry<LocalDate, Money> mapEntry : params.getCompoundingMap().entrySet()) {
            Money value = mapEntry.getValue();
            if (!map.containsKey(mapEntry.getKey())) {
                map.put(mapEntry.getKey(), value.zero());
            }
        }
        return map;
    }
    private LocalDate calculateInterestStartDateForPeriod(final LoanApplicationTerms loanApplicationTerms, LocalDate periodStartDate,
            final LocalDate idealDisbursementDate, final LocalDate firstRepaymentdate,
            final Boolean isInterestChargedFromDateSameAsDisbursalDateEnabled, final LocalDate expectedDisbursementDate) {
        LocalDate periodStartDateApplicableForInterest = periodStartDate;
        if (periodStartDate.isBefore(idealDisbursementDate) || firstRepaymentdate.isAfter(periodStartDate)) {
            if (loanApplicationTerms.getInterestChargedFromLocalDate() != null) {
                if (periodStartDate.isEqual(loanApplicationTerms.getExpectedDisbursementDate())
                        || loanApplicationTerms.getInterestChargedFromLocalDate().isAfter(periodStartDate)) {
                    periodStartDateApplicableForInterest = loanApplicationTerms.getInterestChargedFromLocalDate();
                }
            } else if (loanApplicationTerms.getInterestChargedFromLocalDate() == null
                    && isInterestChargedFromDateSameAsDisbursalDateEnabled) {
                periodStartDateApplicableForInterest = expectedDisbursementDate;
            } else if (periodStartDate.isEqual(loanApplicationTerms.getExpectedDisbursementDate())) {
                periodStartDateApplicableForInterest = idealDisbursementDate;
            }
        }
        return periodStartDateApplicableForInterest;
    }
    private void updateMapWithAmount(final Map<LocalDate, Money> map, final Money amount, final LocalDate amountApplicableDate) {
        Money principalPaid = amount;
        if (map.containsKey(amountApplicableDate)) {
            principalPaid = map.get(amountApplicableDate).plus(principalPaid);
        }
        map.put(amountApplicableDate, principalPaid);
    }
    public abstract PrincipalInterest calculatePrincipalInterestComponentsForPeriod(PaymentPeriodsInOneYearCalculator calculator,
            double interestCalculationGraceOnRepaymentPeriodFraction, Money totalCumulativePrincipal, Money totalCumulativeInterest,
            Money totalInterestDueForLoan, Money cumulatingInterestPaymentDueToGrace, Money outstandingBalance,
            LoanApplicationTerms loanApplicationTerms, int periodNumber, MathContext mc, TreeMap<LocalDate, Money> principalVariation,
            Map<LocalDate, Money> compoundingMap, LocalDate periodStartDate, LocalDate periodEndDate,
            Collection<LoanTermVariationsData> termVariations);
    protected final boolean isLastRepaymentPeriod(final int numberOfRepayments, final int periodNumber) {
        return periodNumber == numberOfRepayments;
    }
    private BigDecimal deriveTotalChargesDueAtTimeOfDisbursement(final Set<LoanCharge> loanCharges) {
        BigDecimal chargesDueAtTimeOfDisbursement = BigDecimal.ZERO;
        for (final LoanCharge loanCharge : loanCharges) {
            if (loanCharge.isDueAtDisbursement()) {
                chargesDueAtTimeOfDisbursement = chargesDueAtTimeOfDisbursement.add(loanCharge.amount());
            }
        }
        return chargesDueAtTimeOfDisbursement;
    }
    private BigDecimal getDisbursementAmount(final LoanApplicationTerms loanApplicationTerms, LocalDate disbursementDate,
            final Collection<LoanScheduleModelPeriod> periods, final BigDecimal chargesDueAtTimeOfDisbursement,
            final Map<LocalDate, Money> disurseDetail, final boolean excludePastUndisbursed) {
        BigDecimal principal = BigDecimal.ZERO;
        if (loanApplicationTerms.getDisbursementDatas().size() == 0) {
            if (loanApplicationTerms.getApprovedPrincipal().getAmount().compareTo(BigDecimal.ZERO) > 0) {
                principal = loanApplicationTerms.getApprovedPrincipal().getAmount();
            } else {
                principal = loanApplicationTerms.getPrincipal().getAmount();
            }
        } else {
            MonetaryCurrency currency = loanApplicationTerms.getPrincipal().getCurrency();
            for (DisbursementData disbursementData : loanApplicationTerms.getDisbursementDatas()) {
                if (disbursementData.disbursementDate().equals(disbursementDate)) {
                    final LoanScheduleModelDisbursementPeriod disbursementPeriod = LoanScheduleModelDisbursementPeriod.disbursement(
                            disbursementData.disbursementDate(), Money.of(currency, disbursementData.amount()),
                            chargesDueAtTimeOfDisbursement);
                    periods.add(disbursementPeriod);
                    principal = principal.add(disbursementData.amount());
                } else if (!excludePastUndisbursed || disbursementData.isDisbursed()
                        || !disbursementData.disbursementDate().isBefore(DateUtils.getLocalDateOfTenant())) {
                    Money prevsum = disurseDetail.get(disbursementData.disbursementDate());
                    BigDecimal sumToNow = BigDecimal.ZERO;
                    if (prevsum != null) {
                        sumToNow = prevsum.getAmount();
                    }
                    sumToNow = sumToNow.add(disbursementData.amount());
                    disurseDetail.put(disbursementData.disbursementDate(), Money.of(currency, sumToNow));
                }
            }
        }
        return principal;
    }
    private Collection<LoanScheduleModelPeriod> createNewLoanScheduleListWithDisbursementDetails(final int numberOfRepayments,
            final LoanApplicationTerms loanApplicationTerms, final BigDecimal chargesDueAtTimeOfDisbursement) {
        Collection<LoanScheduleModelPeriod> periods = null;
        if (loanApplicationTerms.isMultiDisburseLoan()) {
            int multiDisbursalTrancheEntries = loanApplicationTerms.getDisbursementDatas().size();
            if (multiDisbursalTrancheEntries == 0) {
                multiDisbursalTrancheEntries = 1;
            }
            periods = new ArrayList<>(numberOfRepayments + multiDisbursalTrancheEntries);
        } else {
            periods = new ArrayList<>(numberOfRepayments + 1);
            final LoanScheduleModelDisbursementPeriod disbursementPeriod = LoanScheduleModelDisbursementPeriod
                    .disbursement(loanApplicationTerms, chargesDueAtTimeOfDisbursement);
            periods.add(disbursementPeriod);
        }
        return periods;
    }
    private Set<LoanCharge> seperateTotalCompoundingPercentageCharges(final Set<LoanCharge> loanCharges) {
        Set<LoanCharge> interestCharges = new HashSet<>();
        for (final LoanCharge loanCharge : loanCharges) {
            if (loanCharge.isSpecifiedDueDate() && (loanCharge.getChargeCalculation().isPercentageOfInterest()
                    || loanCharge.getChargeCalculation().isPercentageOfAmountAndInterest())) {
                interestCharges.add(loanCharge);
            }
        }
        loanCharges.removeAll(interestCharges);
        return interestCharges;
    }
    private Money cumulativeFeeChargesDueWithin(final LocalDate periodStart, final LocalDate periodEnd, final Set<LoanCharge> loanCharges,
            final MonetaryCurrency monetaryCurrency, final PrincipalInterest principalInterestForThisPeriod, final Money principalDisbursed,
            final Money totalInterestChargedForFullLoanTerm, boolean isInstallmentChargeApplicable) {
        Money cumulative = Money.zero(monetaryCurrency);
        for (final LoanCharge loanCharge : loanCharges) {
            if (!loanCharge.isDueAtDisbursement() && loanCharge.isFeeCharge()) {
                if (loanCharge.isInstalmentFee() && isInstallmentChargeApplicable) {
                    cumulative = calculateInstallmentCharge(principalInterestForThisPeriod, cumulative, loanCharge);
                } else if (loanCharge.isOverdueInstallmentCharge()
                        && loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)
                        && loanCharge.getChargeCalculation().isPercentageBased()) {
                    cumulative = cumulative.plus(loanCharge.chargeAmount());
                } else if (loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)
                        && loanCharge.getChargeCalculation().isPercentageBased()) {
                    cumulative = calculateSpecificDueDateChargeWithPercentage(principalDisbursed, totalInterestChargedForFullLoanTerm,
                            cumulative, loanCharge);
                } else if (loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)) {
                    cumulative = cumulative.plus(loanCharge.amount());
                }
            }
        }
        return cumulative;
    }
    private Money calculateSpecificDueDateChargeWithPercentage(final Money principalDisbursed,
            final Money totalInterestChargedForFullLoanTerm, Money cumulative, final LoanCharge loanCharge) {
        BigDecimal amount = BigDecimal.ZERO;
        if (loanCharge.getChargeCalculation().isPercentageOfAmountAndInterest()) {
            amount = amount.add(principalDisbursed.getAmount()).add(totalInterestChargedForFullLoanTerm.getAmount());
        } else if (loanCharge.getChargeCalculation().isPercentageOfInterest()) {
            amount = amount.add(totalInterestChargedForFullLoanTerm.getAmount());
        } else {
            amount = amount.add(principalDisbursed.getAmount());
        }
        BigDecimal loanChargeAmt = amount.multiply(loanCharge.getPercentage()).divide(BigDecimal.valueOf(100));
        cumulative = cumulative.plus(loanChargeAmt);
        return cumulative;
    }
    private Money calculateInstallmentCharge(final PrincipalInterest principalInterestForThisPeriod, Money cumulative,
            final LoanCharge loanCharge) {
        if (loanCharge.getChargeCalculation().isPercentageBased()) {
            BigDecimal amount = BigDecimal.ZERO;
            if (loanCharge.getChargeCalculation().isPercentageOfAmountAndInterest()) {
                amount = amount.add(principalInterestForThisPeriod.principal().getAmount())
                        .add(principalInterestForThisPeriod.interest().getAmount());
            } else if (loanCharge.getChargeCalculation().isPercentageOfInterest()) {
                amount = amount.add(principalInterestForThisPeriod.interest().getAmount());
            } else {
                amount = amount.add(principalInterestForThisPeriod.principal().getAmount());
            }
            BigDecimal loanChargeAmt = amount.multiply(loanCharge.getPercentage()).divide(BigDecimal.valueOf(100));
            cumulative = cumulative.plus(loanChargeAmt);
        } else {
            cumulative = cumulative.plus(loanCharge.amountOrPercentage());
        }
        return cumulative;
    }
    private Money cumulativePenaltyChargesDueWithin(final LocalDate periodStart, final LocalDate periodEnd,
            final Set<LoanCharge> loanCharges, final MonetaryCurrency monetaryCurrency,
            final PrincipalInterest principalInterestForThisPeriod, final Money principalDisbursed,
            final Money totalInterestChargedForFullLoanTerm, boolean isInstallmentChargeApplicable) {
        Money cumulative = Money.zero(monetaryCurrency);
        for (final LoanCharge loanCharge : loanCharges) {
            if (loanCharge.isPenaltyCharge()) {
                if (loanCharge.isInstalmentFee() && isInstallmentChargeApplicable) {
                    cumulative = calculateInstallmentCharge(principalInterestForThisPeriod, cumulative, loanCharge);
                } else if (loanCharge.isOverdueInstallmentCharge()
                        && loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)
                        && loanCharge.getChargeCalculation().isPercentageBased()) {
                    cumulative = cumulative.plus(loanCharge.chargeAmount());
                } else if (loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)
                        && loanCharge.getChargeCalculation().isPercentageBased()) {
                    cumulative = calculateSpecificDueDateChargeWithPercentage(principalDisbursed, totalInterestChargedForFullLoanTerm,
                            cumulative, loanCharge);
                } else if (loanCharge.isDueForCollectionFromAndUpToAndIncluding(periodStart, periodEnd)) {
                    cumulative = cumulative.plus(loanCharge.amount());
                }
            }
        }
        return cumulative;
    }
    @Override
    public LoanScheduleDTO rescheduleNextInstallments(final MathContext mc, final LoanApplicationTerms loanApplicationTerms, Loan loan,
            final HolidayDetailDTO holidayDetailDTO,
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor, final LocalDate rescheduleFrom) {
        final LocalDate scheduleTillDate = null;
        return rescheduleNextInstallments(mc, loanApplicationTerms, loan, holidayDetailDTO, loanRepaymentScheduleTransactionProcessor,
                rescheduleFrom, scheduleTillDate);
    }
    private LoanScheduleDTO rescheduleNextInstallments(final MathContext mc, final LoanApplicationTerms loanApplicationTerms, Loan loan,
            final HolidayDetailDTO holidayDetailDTO,
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor, final LocalDate rescheduleFrom,
            final LocalDate scheduleTillDate) {
        Collection<RecalculationDetail> recalculationDetails = new ArrayList<>();
        List<LoanTransaction> transactions = loan.getLoanTransactions();
        for (LoanTransaction loanTransaction : transactions) {
            if (loanTransaction.isPaymentTransaction()) {
                recalculationDetails.add(new RecalculationDetail(loanTransaction.getTransactionDate(),
                        LoanTransaction.copyTransactionProperties(loanTransaction)));
            }
        }
        final boolean applyInterestRecalculation = loanApplicationTerms.isInterestRecalculationEnabled();
        LoanScheduleParams loanScheduleParams = null;
        Collection<LoanScheduleModelPeriod> periods = new ArrayList<>();
        final List<LoanRepaymentScheduleInstallment> retainedInstallments = new ArrayList<>();
        if (rescheduleFrom != null) {
            Money principalToBeScheduled = getPrincipalToBeScheduled(loanApplicationTerms);
            Money outstandingBalance = principalToBeScheduled;
            Money outstandingBalanceAsPerRest = outstandingBalance;
            final BigDecimal chargesDueAtTimeOfDisbursement = deriveTotalChargesDueAtTimeOfDisbursement(loan.charges());
            periods = createNewLoanScheduleListWithDisbursementDetails(loanApplicationTerms.fetchNumberOfRepaymentsAfterExceptions(),
                    loanApplicationTerms, chargesDueAtTimeOfDisbursement);
            MonetaryCurrency currency = outstandingBalance.getCurrency();
            Money reducePrincipal = outstandingBalanceAsPerRest.zero();
            Money uncompoundedAmount = outstandingBalanceAsPerRest.zero();
            final Map<LocalDate, Money> principalPortionMap = new HashMap<>();
            final Map<LocalDate, Money> latePaymentMap = new HashMap<>();
            final TreeMap<LocalDate, Money> compoundingMap = new TreeMap<>();
            final Map<LocalDate, Map<LocalDate, Money>> compoundingDateVariations = new HashMap<>();
            LocalDate currentDate = DateUtils.getBusinessLocalDate();
            LocalDate lastRestDate = currentDate;
            if (loanApplicationTerms.isInterestRecalculationEnabled()) {
                lastRestDate = getNextRestScheduleDate(currentDate.minusDays(1), loanApplicationTerms, holidayDetailDTO);
            }
            LocalDate actualRepaymentDate = loanApplicationTerms.getExpectedDisbursementDate();
            boolean isFirstRepayment = true;
            Money totalCumulativePrincipal = principalToBeScheduled.zero();
            Money totalCumulativeInterest = principalToBeScheduled.zero();
            Money totalFeeChargesCharged = principalToBeScheduled.zero().plus(chargesDueAtTimeOfDisbursement);
            Money totalPenaltyChargesCharged = principalToBeScheduled.zero();
            Money totalRepaymentExpected = principalToBeScheduled.zero();
            int periodNumber = 1;
            int instalmentNumber = 1;
            LocalDate lastInstallmentDate = actualRepaymentDate;
            LocalDate periodStartDate = loanApplicationTerms.getExpectedDisbursementDate();
            updateAmortization(mc, loanApplicationTerms, periodNumber, outstandingBalance);
            final Map<LocalDate, Money> disburseDetailMap = new HashMap<>();
            if (loanApplicationTerms.isMultiDisburseLoan()) {
                BigDecimal disburseAmt = getDisbursementAmount(loanApplicationTerms, loanApplicationTerms.getExpectedDisbursementDate(),
                        periods, chargesDueAtTimeOfDisbursement, disburseDetailMap, true);
                outstandingBalance = outstandingBalance.zero().plus(disburseAmt);
                outstandingBalanceAsPerRest = outstandingBalance;
                principalToBeScheduled = principalToBeScheduled.zero().plus(disburseAmt);
            }
            int loanTermInDays = 0;
            List<LoanTermVariationsData> exceptionDataList = loanApplicationTerms.getLoanTermVariations().getExceptionData();
            final ListIterator<LoanTermVariationsData> exceptionDataListIterator = exceptionDataList.listIterator();
            LoanTermVariationParams loanTermVariationParams = null;
            final List<LoanRepaymentScheduleInstallment> processInstallmentsInstallments = fetchRetainedInstallments(
                    loan.getRepaymentScheduleInstallments(), rescheduleFrom, currency);
            final List<LoanRepaymentScheduleInstallment> newRepaymentScheduleInstallments = new ArrayList<>();
            for (LoanRepaymentScheduleInstallment installment : processInstallmentsInstallments) {
                if (installment.getDueDate().isAfter(lastInstallmentDate)) {
                    if (totalCumulativePrincipal.isGreaterThanOrEqualTo(loanApplicationTerms.getTotalDisbursedAmount())) {
                        break;
                    }
                    ArrayList<LoanTermVariationsData> dueDateVariationsDataList = new ArrayList<>();
                    do {
                        actualRepaymentDate = this.scheduledDateGenerator.generateNextRepaymentDate(actualRepaymentDate,
                                loanApplicationTerms, isFirstRepayment);
                        if (actualRepaymentDate.isAfter(rescheduleFrom) || actualRepaymentDate.isEqual(rescheduleFrom)) {
                            actualRepaymentDate = lastInstallmentDate;
                        }
                        isFirstRepayment = false;
                        LocalDate prevLastInstDate = lastInstallmentDate;
                        lastInstallmentDate = this.scheduledDateGenerator
                                .adjustRepaymentDate(actualRepaymentDate, loanApplicationTerms, holidayDetailDTO).getChangedScheduleDate();
                        LocalDate modifiedLastInstDate = null;
                        LoanTermVariationsData variation1 = null;
                        while (loanApplicationTerms.getLoanTermVariations().hasDueDateVariation(lastInstallmentDate)) {
                            LoanTermVariationsData variation = loanApplicationTerms.getLoanTermVariations().nextDueDateVariation();
                            if (!variation.isSpecificToInstallment()) {
                                modifiedLastInstDate = variation.getDateValue();
                                variation1 = variation;
                            }
                        }
                        if (!lastInstallmentDate.isEqual(installment.getDueDate())
                                && !installment.getDueDate().equals(modifiedLastInstDate)) {
                            lastInstallmentDate = prevLastInstDate;
                            actualRepaymentDate = lastInstallmentDate;
                            if (modifiedLastInstDate != null) {
                                loanApplicationTerms.getLoanTermVariations().previousDueDateVariation();
                            }
                        } else if (installment.getDueDate().equals(modifiedLastInstDate)) {
                            actualRepaymentDate = modifiedLastInstDate;
                            lastInstallmentDate = actualRepaymentDate;
                            dueDateVariationsDataList.add(variation1);
                        }
                        loanTermVariationParams = applyExceptionLoanTermVariations(loanApplicationTerms, lastInstallmentDate,
                                exceptionDataListIterator, instalmentNumber, totalCumulativePrincipal, totalCumulativeInterest, mc);
                    } while (loanTermVariationParams != null && loanTermVariationParams.isSkipPeriod());
                    periodNumber++;
                    for (LoanTermVariationsData dueDateVariation : dueDateVariationsDataList) {
                        dueDateVariation.setProcessed(true);
                    }
                    if (loanTermVariationParams != null && loanTermVariationParams.isSkipPeriod()) {
                        ArrayList<LoanTermVariationsData> variationsDataList = loanTermVariationParams.getVariationsDataList();
                        for (LoanTermVariationsData variationsData : variationsDataList) {
                            variationsData.setProcessed(true);
                        }
                    }
                }
                for (Map.Entry<LocalDate, Money> disburseDetail : disburseDetailMap.entrySet()) {
                    if (disburseDetail.getKey().isAfter(installment.getFromDate())
                            && !disburseDetail.getKey().isAfter(installment.getDueDate())) {
                        final LoanScheduleModelDisbursementPeriod disbursementPeriod = LoanScheduleModelDisbursementPeriod
                                .disbursement(disburseDetail.getKey(), disburseDetail.getValue(), chargesDueAtTimeOfDisbursement);
                        periods.add(disbursementPeriod);
                        outstandingBalance = outstandingBalance.plus(disburseDetail.getValue());
                        principalToBeScheduled = principalToBeScheduled.plus(disburseDetail.getValue());
                    }
                }
                periodStartDate = installment.getDueDate();
                installment.resetDerivedComponents();
                newRepaymentScheduleInstallments.add(installment);
                outstandingBalance = outstandingBalance.minus(installment.getPrincipal(currency));
                final LoanScheduleModelPeriod loanScheduleModelPeriod = createLoanScheduleModelPeriod(installment, outstandingBalance);
                periods.add(loanScheduleModelPeriod);
                totalCumulativePrincipal = totalCumulativePrincipal.plus(installment.getPrincipal(currency));
                totalCumulativeInterest = totalCumulativeInterest.plus(installment.getInterestCharged(currency));
                totalFeeChargesCharged = totalFeeChargesCharged.plus(installment.getFeeChargesCharged(currency));
                totalPenaltyChargesCharged = totalPenaltyChargesCharged.plus(installment.getPenaltyChargesCharged(currency));
                instalmentNumber++;
                loanTermInDays = Math.toIntExact(ChronoUnit.DAYS.between(installment.getFromDate(), installment.getDueDate()));
                if (loanApplicationTerms.isInterestRecalculationEnabled()) {
                    Collection<RecalculationDetail> applicableTransactions = getApplicableTransactionsForPeriod(applyInterestRecalculation,
                            installment.getDueDate(), recalculationDetails);
                    Money principalPortionCalculated = principalToBeScheduled.zero();
                    if (!installment.isRecalculatedInterestComponent()) {
                        principalPortionCalculated = calculateExpectedPrincipalPortion(installment.getInterestCharged(currency),
                                loanApplicationTerms);
                    }
                    Money actualPrincipalPortion = principalPortionCalculated.minus(reducePrincipal);
                    if (actualPrincipalPortion.isLessThanZero()) {
                        actualPrincipalPortion = principalPortionCalculated.zero();
                    }
                    Money unprocessed = updateEarlyPaidAmountsToMap(loanApplicationTerms, holidayDetailDTO,
                            loanRepaymentScheduleTransactionProcessor, newRepaymentScheduleInstallments, currency, principalPortionMap,
                            installment, applicableTransactions, actualPrincipalPortion);
                    int period = periodNumber;
                    if (!lastInstallmentDate.isEqual(installment.getDueDate())) {
                        period--;
                    }
                    reducePrincipal = fetchEarlyPaidAmount(installment.getPrincipal(currency), principalPortionCalculated, reducePrincipal,
                            loanApplicationTerms, totalCumulativePrincipal, period, mc);
                    LocalDate amountApplicableDate = null;
                    if (loanApplicationTerms.getRestCalendarInstance() != null) {
                        amountApplicableDate = getNextRestScheduleDate(installment.getDueDate().minusDays(1), loanApplicationTerms,
                                holidayDetailDTO);
                    }
                    updateMapWithAmount(principalPortionMap, installment.getPrincipal(currency).minus(unprocessed), amountApplicableDate);
                    uncompoundedAmount = updateCompoundingDetailsForPartialScheduleGeneration(installment, loanApplicationTerms,
                            principalPortionMap, compoundingDateVariations, uncompoundedAmount, applicableTransactions, lastRestDate,
                            holidayDetailDTO);
                    outstandingBalanceAsPerRest = updateBalanceForInterestCalculation(principalPortionMap, installment.getDueDate(),
                            outstandingBalanceAsPerRest, false);
                    outstandingBalanceAsPerRest = updateBalanceForInterestCalculation(disburseDetailMap, installment.getDueDate(),
                            outstandingBalanceAsPerRest, true);
                    updateLatePaymentsToMap(loanApplicationTerms, holidayDetailDTO, currency, latePaymentMap, lastInstallmentDate,
                            newRepaymentScheduleInstallments, true, lastRestDate);
                } else {
                    outstandingBalanceAsPerRest = outstandingBalance;
                }
            }
            totalRepaymentExpected = totalCumulativePrincipal.plus(totalCumulativeInterest).plus(totalFeeChargesCharged)
                    .plus(totalPenaltyChargesCharged);
            if (!newRepaymentScheduleInstallments.isEmpty() && totalCumulativeInterest.isGreaterThanZero()) {
                Money totalOutstandingInterestPaymentDueToGrace = Money.zero(currency);
                loanScheduleParams = LoanScheduleParams.createLoanScheduleParamsForPartialUpdate(periodNumber, instalmentNumber,
                        loanTermInDays, periodStartDate, actualRepaymentDate, totalCumulativePrincipal, totalCumulativeInterest,
                        totalFeeChargesCharged, totalPenaltyChargesCharged, totalRepaymentExpected,
                        totalOutstandingInterestPaymentDueToGrace, reducePrincipal, principalPortionMap, latePaymentMap, compoundingMap,
                        uncompoundedAmount, disburseDetailMap, principalToBeScheduled, outstandingBalance, outstandingBalanceAsPerRest,
                        newRepaymentScheduleInstallments, recalculationDetails, loanRepaymentScheduleTransactionProcessor, scheduleTillDate,
                        currency, applyInterestRecalculation);
                retainedInstallments.addAll(newRepaymentScheduleInstallments);
                loanScheduleParams.getCompoundingDateVariations().putAll(compoundingDateVariations);
                loanApplicationTerms.updateTotalInterestDue(Money.of(currency, loan.getLoanSummary().getTotalInterestCharged()));
            } else {
                loanApplicationTerms.getLoanTermVariations().resetVariations();
            }
        }
        if (loanScheduleParams == null) {
            loanScheduleParams = LoanScheduleParams.createLoanScheduleParamsForCompleteUpdate(recalculationDetails,
                    loanRepaymentScheduleTransactionProcessor, scheduleTillDate, applyInterestRecalculation);
            periods.clear();
        }
        if (retainedInstallments.size() > 0
                && retainedInstallments.get(retainedInstallments.size() - 1).getRescheduleInterestPortion() != null) {
            loanApplicationTerms.setInterestTobeApproppriated(
                    Money.of(loan.getCurrency(), retainedInstallments.get(retainedInstallments.size() - 1).getRescheduleInterestPortion()));
        }
        LoanScheduleModel loanScheduleModel = generate(mc, loanApplicationTerms, loan.charges(), holidayDetailDTO, loanScheduleParams);
        for (LoanScheduleModelPeriod loanScheduleModelPeriod : loanScheduleModel.getPeriods()) {
            if (loanScheduleModelPeriod.isRepaymentPeriod()) {
                addLoanRepaymentScheduleInstallment(retainedInstallments, loanScheduleModelPeriod);
            }
        }
        periods.addAll(loanScheduleModel.getPeriods());
        LoanScheduleModel loanScheduleModelwithPeriodChanges = LoanScheduleModel.withLoanScheduleModelPeriods(periods, loanScheduleModel);
        return LoanScheduleDTO.from(retainedInstallments, loanScheduleModelwithPeriodChanges);
    }
    public List<LoanRepaymentScheduleInstallment> fetchRetainedInstallments(
            final List<LoanRepaymentScheduleInstallment> repaymentScheduleInstallments, final LocalDate rescheduleFrom,
            MonetaryCurrency currency) {
        List<LoanRepaymentScheduleInstallment> newRepaymentScheduleInstallments = new ArrayList<>();
        int lastInterestAvilablePeriod = 0;
        int processedPeriod = 0;
        for (LoanRepaymentScheduleInstallment installment : repaymentScheduleInstallments) {
            if (installment.getDueDate().isBefore(rescheduleFrom)) {
                newRepaymentScheduleInstallments.add(installment);
                if (installment.getInterestCharged(currency).isGreaterThanZero()) {
                    lastInterestAvilablePeriod = installment.getInstallmentNumber();
                }
                processedPeriod = installment.getInstallmentNumber();
            } else {
                break;
            }
        }
        if (lastInterestAvilablePeriod != processedPeriod) {
            final List<LoanRepaymentScheduleInstallment> retainRepaymentScheduleInstallments = new ArrayList<>();
            for (LoanRepaymentScheduleInstallment installment : newRepaymentScheduleInstallments) {
                if (installment.getInstallmentNumber() <= lastInterestAvilablePeriod) {
                    retainRepaymentScheduleInstallments.add(installment);
                }
            }
            newRepaymentScheduleInstallments.retainAll(retainRepaymentScheduleInstallments);
        }
        return newRepaymentScheduleInstallments;
    }
    private Money updateEarlyPaidAmountsToMap(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO,
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor,
            final List<LoanRepaymentScheduleInstallment> newRepaymentScheduleInstallments, MonetaryCurrency currency,
            final Map<LocalDate, Money> principalPortionMap, LoanRepaymentScheduleInstallment installment,
            Collection<RecalculationDetail> applicableTransactions, Money actualPrincipalPortion) {
        Money unprocessed = Money.zero(currency);
        Money totalUnprocessed = Money.zero(currency);
        for (RecalculationDetail detail : applicableTransactions) {
            if (!detail.isProcessed()) {
                Money principalProcessed = installment.getPrincipalCompleted(currency);
                List<LoanTransaction> currentTransactions = new ArrayList<>(2);
                currentTransactions.add(detail.getTransaction());
                loanRepaymentScheduleTransactionProcessor.handleRepaymentSchedule(currentTransactions, currency,
                        newRepaymentScheduleInstallments);
                Money principalPaidWithTransaction = installment.getPrincipalCompleted(currency).minus(principalProcessed);
                Money totalEarlyPayment = installment.getPrincipalCompleted(currency).minus(actualPrincipalPortion);
                if (totalEarlyPayment.isGreaterThanZero()) {
                    unprocessed = principalPaidWithTransaction;
                    if (principalPaidWithTransaction.isGreaterThan(totalEarlyPayment)) {
                        unprocessed = totalEarlyPayment;
                    }
                }
                LocalDate applicableDate = getNextRestScheduleDate(detail.getTransactionDate().minusDays(1), loanApplicationTerms,
                        holidayDetailDTO);
                updateMapWithAmount(principalPortionMap, unprocessed, applicableDate);
                totalUnprocessed = totalUnprocessed.plus(unprocessed);
            }
        }
        return totalUnprocessed;
    }
    private Money updateCompoundingDetailsForPartialScheduleGeneration(final LoanRepaymentScheduleInstallment installment,
            LoanApplicationTerms loanApplicationTerms, Map<LocalDate, Money> principalMap,
            final Map<LocalDate, Map<LocalDate, Money>> compoundingDateVariations, final Money uncompoundedAmount,
            final Collection<RecalculationDetail> applicableTransactions, LocalDate lastRestDate, HolidayDetailDTO holidayDetailDTO) {
        Money uncompounded = uncompoundedAmount;
        MonetaryCurrency currency = uncompoundedAmount.getCurrency();
        for (RecalculationDetail detail : applicableTransactions) {
            LocalDate applicableDate = getNextRestScheduleDate(detail.getTransactionDate().minusDays(1), loanApplicationTerms,
                    holidayDetailDTO);
            adjustCompoundedAmountWithPaidDetail(principalMap, lastRestDate, applicableDate, detail.getTransaction(), loanApplicationTerms,
                    currency);
        }
        Money amountCharged = getIncomeForCompounding(loanApplicationTerms, currency, installment);
        final Set<LoanInterestRecalcualtionAdditionalDetails> details = installment.getLoanCompoundingDetails();
        Money totalCompounded = Money.zero(currency);
        Map<LocalDate, Money> compoundingMap = new TreeMap<>();
        for (LoanInterestRecalcualtionAdditionalDetails additionalDetails : details) {
            LocalDate effectiveDate = additionalDetails.getEffectiveDate();
            if (loanApplicationTerms.allowCompoundingOnEod()) {
                effectiveDate = effectiveDate.plusDays(1);
            }
            compoundingMap.put(effectiveDate, Money.of(currency, additionalDetails.getAmount()));
            totalCompounded = totalCompounded.plus(additionalDetails.getAmount());
            updateMapWithAmount(principalMap, Money.of(currency, additionalDetails.getAmount()).negated(), effectiveDate);
        }
        compoundingDateVariations.put(installment.getFromDate(), compoundingMap);
        if (totalCompounded.isGreaterThanZero()) {
            final boolean isPastDate = installment.getDueDate().isBefore(DateUtils.getBusinessLocalDate());
            final LocalDate restDate = getNextRestScheduleDate(installment.getDueDate().minusDays(1), loanApplicationTerms,
                    holidayDetailDTO);
            if (isPastDate) {
                updateMapWithAmount(principalMap, totalCompounded, lastRestDate);
            } else {
                updateMapWithAmount(principalMap, totalCompounded, restDate);
            }
            uncompounded = amountCharged.plus(uncompounded).minus(totalCompounded);
        } else {
            uncompounded = uncompounded.plus(amountCharged);
        }
        return uncompounded;
    }
    private void updateAmortization(final MathContext mc, final LoanApplicationTerms loanApplicationTerms, int periodNumber,
            Money outstandingBalance) {
        if (loanApplicationTerms.getAmortizationMethod().isEqualInstallment()) {
            updateFixedInstallmentAmount(mc, loanApplicationTerms, periodNumber, outstandingBalance);
        } else {
            loanApplicationTerms.updateFixedPrincipalAmount(mc, periodNumber, outstandingBalance);
        }
    }
    private Money fetchEarlyPaidAmount(final Money principalPortion, final Money principalPortionCalculated, final Money reducePrincipal,
            final LoanApplicationTerms applicationTerms, final Money totalCumulativePrincipal, int periodNumber, final MathContext mc) {
        Money existingEarlyPayment = reducePrincipal.minus(principalPortionCalculated);
        Money earlyPaidAmount = principalPortion.plus(existingEarlyPayment);
        if (existingEarlyPayment.isLessThanZero()) {
            existingEarlyPayment = existingEarlyPayment.zero();
        }
        boolean isEarlyPaid = earlyPaidAmount.isGreaterThan(existingEarlyPayment);
        if (earlyPaidAmount.isLessThanZero()) {
            earlyPaidAmount = earlyPaidAmount.zero();
        }
        if (isEarlyPaid && applicationTerms.getRescheduleStrategyMethod() != null) {
            switch (applicationTerms.getRescheduleStrategyMethod()) {
                case REDUCE_EMI_AMOUNT:
                    adjustInstallmentOrPrincipalAmount(applicationTerms, totalCumulativePrincipal, periodNumber, mc);
                    earlyPaidAmount = earlyPaidAmount.zero();
                break;
                case REDUCE_NUMBER_OF_INSTALLMENTS:
                    earlyPaidAmount = earlyPaidAmount.zero();
                break;
                case RESCHEDULE_NEXT_REPAYMENTS:
                break;
                default:
                break;
            }
        }
        return earlyPaidAmount;
    }
    private Money calculateExpectedPrincipalPortion(final Money interestPortion, final LoanApplicationTerms applicationTerms) {
        Money principalPortionCalculated = interestPortion.zero();
        if (applicationTerms.getAmortizationMethod().isEqualInstallment()) {
            principalPortionCalculated = principalPortionCalculated.plus(applicationTerms.getFixedEmiAmount()).minus(interestPortion);
        } else {
            principalPortionCalculated = principalPortionCalculated.plus(applicationTerms.getFixedPrincipalAmount());
        }
        return principalPortionCalculated;
    }
    private LoanRepaymentScheduleInstallment addLoanRepaymentScheduleInstallment(final List<LoanRepaymentScheduleInstallment> installments,
            final LoanScheduleModelPeriod scheduledLoanInstallment) {
        LoanRepaymentScheduleInstallment installment = null;
        if (scheduledLoanInstallment.isRepaymentPeriod()) {
            installment = new LoanRepaymentScheduleInstallment(null, scheduledLoanInstallment.periodNumber(),
                    scheduledLoanInstallment.periodFromDate(), scheduledLoanInstallment.periodDueDate(),
                    scheduledLoanInstallment.principalDue(), scheduledLoanInstallment.interestDue(),
                    scheduledLoanInstallment.feeChargesDue(), scheduledLoanInstallment.penaltyChargesDue(),
                    scheduledLoanInstallment.isRecalculatedInterestComponent(), scheduledLoanInstallment.getLoanCompoundingDetails(),
                    scheduledLoanInstallment.rescheduleInterestPortion());
            installments.add(installment);
        }
        return installment;
    }
    private LoanScheduleModelPeriod createLoanScheduleModelPeriod(final LoanRepaymentScheduleInstallment installment,
            final Money outstandingPrincipal) {
        final MonetaryCurrency currency = outstandingPrincipal.getCurrency();
        LoanScheduleModelPeriod scheduledLoanInstallment = LoanScheduleModelRepaymentPeriod.repayment(installment.getInstallmentNumber(),
                installment.getFromDate(), installment.getDueDate(), installment.getPrincipal(currency), outstandingPrincipal,
                installment.getInterestCharged(currency), installment.getFeeChargesCharged(currency),
                installment.getPenaltyChargesCharged(currency), installment.getDue(currency),
                installment.isRecalculatedInterestComponent());
        return scheduledLoanInstallment;
    }
    private LocalDate getNextRestScheduleDate(LocalDate startDate, LoanApplicationTerms loanApplicationTerms,
            final HolidayDetailDTO holidayDetailDTO) {
        LocalDate nextScheduleDate = null;
        if (loanApplicationTerms.getRecalculationFrequencyType().isSameAsRepayment()) {
            nextScheduleDate = this.scheduledDateGenerator.generateNextScheduleDateStartingFromDisburseDateOrRescheduleDate(startDate,
                    loanApplicationTerms, holidayDetailDTO);
        } else {
            CalendarInstance calendarInstance = loanApplicationTerms.getRestCalendarInstance();
            nextScheduleDate = CalendarUtils.getNextScheduleDate(calendarInstance.getCalendar(), startDate);
        }
        return nextScheduleDate;
    }
    private LocalDate getNextCompoundScheduleDate(LocalDate startDate, LoanApplicationTerms loanApplicationTerms,
            final HolidayDetailDTO holidayDetailDTO) {
        LocalDate nextScheduleDate = null;
        if (!loanApplicationTerms.getInterestRecalculationCompoundingMethod().isCompoundingEnabled()) {
            return null;
        }
        if (loanApplicationTerms.getCompoundingFrequencyType().isSameAsRepayment()) {
            nextScheduleDate = this.scheduledDateGenerator.generateNextScheduleDateStartingFromDisburseDate(startDate, loanApplicationTerms,
                    holidayDetailDTO);
        } else {
            CalendarInstance calendarInstance = loanApplicationTerms.getCompoundingCalendarInstance();
            nextScheduleDate = CalendarUtils.getNextScheduleDate(calendarInstance.getCalendar(), startDate);
            if (loanApplicationTerms.allowCompoundingOnEod()) {
                nextScheduleDate = nextScheduleDate.plusDays(1);
            }
        }
        return nextScheduleDate;
    }
    @Override
    public LoanRepaymentScheduleInstallment calculatePrepaymentAmount(final MonetaryCurrency currency, final LocalDate onDate,
            final LoanApplicationTerms loanApplicationTerms, final MathContext mc, Loan loan, final HolidayDetailDTO holidayDetailDTO,
            final LoanRepaymentScheduleTransactionProcessor loanRepaymentScheduleTransactionProcessor) {
        LocalDate calculateTill = onDate;
        if (loanApplicationTerms.getPreClosureInterestCalculationStrategy().calculateTillRestFrequencyEnabled()) {
            calculateTill = getNextRestScheduleDate(onDate.minusDays(1), loanApplicationTerms, holidayDetailDTO);
        }
        LoanScheduleDTO loanScheduleDTO = rescheduleNextInstallments(mc, loanApplicationTerms, loan, holidayDetailDTO,
                loanRepaymentScheduleTransactionProcessor, onDate, calculateTill);
        List<LoanTransaction> loanTransactions = loan.retreiveListOfTransactionsPostDisbursementExcludeAccruals();
        loanRepaymentScheduleTransactionProcessor.handleTransaction(loanApplicationTerms.getExpectedDisbursementDate(), loanTransactions,
                currency, loanScheduleDTO.getInstallments(), loan.charges());
        Money feeCharges = Money.zero(currency);
        Money penaltyCharges = Money.zero(currency);
        Money totalPrincipal = Money.zero(currency);
        Money totalInterest = Money.zero(currency);
        for (final LoanRepaymentScheduleInstallment currentInstallment : loanScheduleDTO.getInstallments()) {
            if (currentInstallment.isNotFullyPaidOff()) {
                totalPrincipal = totalPrincipal.plus(currentInstallment.getPrincipalOutstanding(currency));
                totalInterest = totalInterest.plus(currentInstallment.getInterestOutstanding(currency));
                feeCharges = feeCharges.plus(currentInstallment.getFeeChargesOutstanding(currency));
                penaltyCharges = penaltyCharges.plus(currentInstallment.getPenaltyChargesOutstanding(currency));
            }
        }
        final Set<LoanInterestRecalcualtionAdditionalDetails> compoundingDetails = null;
        return new LoanRepaymentScheduleInstallment(null, 0, onDate, onDate, totalPrincipal.getAmount(), totalInterest.getAmount(),
                feeCharges.getAmount(), penaltyCharges.getAmount(), false, compoundingDetails);
    }
    private static final class LoanTermVariationParams {
        private final boolean skipPeriod;
        private final boolean recalculateAmounts;
        private final LocalDate scheduledDueDate;
        private final ArrayList<LoanTermVariationsData> variationsData;
        LoanTermVariationParams(final boolean skipPeriod, final boolean recalculateAmounts, final LocalDate scheduledDueDate,
                final ArrayList<LoanTermVariationsData> variationsData) {
            this.skipPeriod = skipPeriod;
            this.recalculateAmounts = recalculateAmounts;
            this.scheduledDueDate = scheduledDueDate;
            this.variationsData = variationsData;
        }
        public boolean isSkipPeriod() {
            return this.skipPeriod;
        }
        public boolean isRecalculateAmounts() {
            return this.recalculateAmounts;
        }
        public LocalDate getScheduledDueDate() {
            return this.scheduledDueDate;
        }
        public ArrayList<LoanTermVariationsData> getVariationsDataList() {
            return this.variationsData;
        }
    }
    private static final class ScheduleCurrentPeriodParams {
        Money earlyPaidAmount;
        LoanScheduleModelPeriod lastInstallment;
        boolean skipCurrentLoop;
        Money interestForThisPeriod;
        Money principalForThisPeriod;
        Money feeChargesForInstallment;
        Money penaltyChargesForInstallment;
        Money reducedBalance;
        boolean isEmiAmountChanged;
        double interestCalculationGraceOnRepaymentPeriodFraction;
        ScheduleCurrentPeriodParams(final MonetaryCurrency currency, double interestCalculationGraceOnRepaymentPeriodFraction) {
            this.earlyPaidAmount = Money.zero(currency);
            this.lastInstallment = null;
            this.skipCurrentLoop = false;
            this.interestForThisPeriod = Money.zero(currency);
            this.principalForThisPeriod = Money.zero(currency);
            this.reducedBalance = Money.zero(currency);
            this.feeChargesForInstallment = Money.zero(currency);
            this.penaltyChargesForInstallment = Money.zero(currency);
            this.isEmiAmountChanged = false;
            this.interestCalculationGraceOnRepaymentPeriodFraction = interestCalculationGraceOnRepaymentPeriodFraction;
        }
        public Money getEarlyPaidAmount() {
            return this.earlyPaidAmount;
        }
        public void plusEarlyPaidAmount(Money earlyPaidAmount) {
            this.earlyPaidAmount = this.earlyPaidAmount.plus(earlyPaidAmount);
        }
        public void minusEarlyPaidAmount(Money earlyPaidAmount) {
            this.earlyPaidAmount = this.earlyPaidAmount.minus(earlyPaidAmount);
        }
        public LoanScheduleModelPeriod getLastInstallment() {
            return this.lastInstallment;
        }
        public void setLastInstallment(LoanScheduleModelPeriod lastInstallment) {
            this.lastInstallment = lastInstallment;
        }
        public boolean isSkipCurrentLoop() {
            return this.skipCurrentLoop;
        }
        public void setSkipCurrentLoop(boolean skipCurrentLoop) {
            this.skipCurrentLoop = skipCurrentLoop;
        }
        public Money getInterestForThisPeriod() {
            return this.interestForThisPeriod;
        }
        public void setInterestForThisPeriod(Money interestForThisPeriod) {
            this.interestForThisPeriod = interestForThisPeriod;
        }
        public void minusInterestForThisPeriod(Money interestForThisPeriod) {
            this.interestForThisPeriod = this.interestForThisPeriod.minus(interestForThisPeriod);
        }
        public Money getPrincipalForThisPeriod() {
            return this.principalForThisPeriod;
        }
        public void setPrincipalForThisPeriod(Money principalForThisPeriod) {
            this.principalForThisPeriod = principalForThisPeriod;
        }
        public void plusPrincipalForThisPeriod(Money principalForThisPeriod) {
            this.principalForThisPeriod = this.principalForThisPeriod.plus(principalForThisPeriod);
        }
        public void minusPrincipalForThisPeriod(Money principalForThisPeriod) {
            this.principalForThisPeriod = this.principalForThisPeriod.minus(principalForThisPeriod);
        }
        public Money getReducedBalance() {
            return this.reducedBalance;
        }
        public void setReducedBalance(Money reducedBalance) {
            this.reducedBalance = reducedBalance;
        }
        public Money getFeeChargesForInstallment() {
            return this.feeChargesForInstallment;
        }
        public void setFeeChargesForInstallment(Money feeChargesForInstallment) {
            this.feeChargesForInstallment = feeChargesForInstallment;
        }
        public void minusFeeChargesForInstallment(Money feeChargesForInstallment) {
            this.feeChargesForInstallment = this.feeChargesForInstallment.minus(feeChargesForInstallment);
        }
        public Money getPenaltyChargesForInstallment() {
            return this.penaltyChargesForInstallment;
        }
        public void setPenaltyChargesForInstallment(Money penaltyChargesForInstallment) {
            this.penaltyChargesForInstallment = penaltyChargesForInstallment;
        }
        public void minusPenaltyChargesForInstallment(Money penaltyChargesForInstallment) {
            this.penaltyChargesForInstallment = this.penaltyChargesForInstallment.minus(penaltyChargesForInstallment);
        }
        public Money fetchTotalAmountForPeriod() {
            return this.principalForThisPeriod.plus(interestForThisPeriod).plus(feeChargesForInstallment)
                    .plus(penaltyChargesForInstallment);
        }
        public boolean isEmiAmountChanged() {
            return this.isEmiAmountChanged;
        }
        public void setEmiAmountChanged(boolean isEmiAmountChanged) {
            this.isEmiAmountChanged = isEmiAmountChanged;
        }
        public double getInterestCalculationGraceOnRepaymentPeriodFraction() {
            return this.interestCalculationGraceOnRepaymentPeriodFraction;
        }
    }
}
