
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.loanaccount.domain.LoanInterestRecalcualtionAdditionalDetails;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanSchedulePeriodData;
public final class LoanScheduleModelDisbursementPeriod implements LoanScheduleModelPeriod {
    @SuppressWarnings("unused")
    private final Integer periodNumber;
    private final LocalDate disbursementDate;
    private final Money principalDisbursed;
    private final BigDecimal chargesDueAtTimeOfDisbursement;
    private boolean isEMIFixedSpecificToInstallment = false;
    public static LoanScheduleModelDisbursementPeriod disbursement(final LoanApplicationTerms loanApplicationTerms,
            final BigDecimal chargesDueAtTimeOfDisbursement) {
        final int periodNumber = 0;
        return new LoanScheduleModelDisbursementPeriod(periodNumber, loanApplicationTerms.getExpectedDisbursementDate(),
                loanApplicationTerms.getPrincipal(), chargesDueAtTimeOfDisbursement);
    }
    public static LoanScheduleModelDisbursementPeriod disbursement(final LocalDate disbursementDate, final Money principalDisbursed,
            final BigDecimal chargesDueAtTimeOfDisbursement) {
        return new LoanScheduleModelDisbursementPeriod(null, disbursementDate, principalDisbursed, chargesDueAtTimeOfDisbursement);
    }
    private LoanScheduleModelDisbursementPeriod(final Integer periodNumber, final LocalDate disbursementDate,
            final Money principalDisbursed, final BigDecimal chargesDueAtTimeOfDisbursement) {
        this.periodNumber = periodNumber;
        this.disbursementDate = disbursementDate;
        this.principalDisbursed = principalDisbursed;
        this.chargesDueAtTimeOfDisbursement = chargesDueAtTimeOfDisbursement;
    }
    @Override
    public LoanSchedulePeriodData toData() {
        return LoanSchedulePeriodData.disbursementOnlyPeriod(this.disbursementDate, this.principalDisbursed.getAmount(),
                this.chargesDueAtTimeOfDisbursement, false);
    }
    @Override
    public boolean isRepaymentPeriod() {
        return false;
    }
    @Override
    public Integer periodNumber() {
        return null;
    }
    @Override
    public LocalDate periodFromDate() {
        return null;
    }
    @Override
    public LocalDate periodDueDate() {
        return null;
    }
    @Override
    public BigDecimal principalDue() {
        return null;
    }
    @Override
    public BigDecimal interestDue() {
        return null;
    }
    @Override
    public BigDecimal feeChargesDue() {
        return null;
    }
    @Override
    public BigDecimal penaltyChargesDue() {
        return null;
    }
    @Override
    public void addLoanCharges(@SuppressWarnings("unused") BigDecimal feeCharge, @SuppressWarnings("unused") BigDecimal penaltyCharge) {
        return;
    }
    @Override
    public boolean isRecalculatedInterestComponent() {
        return false;
    }
    @Override
    public void addPrincipalAmount(@SuppressWarnings("unused") Money principalDue) {
        return;
    }
    @Override
    public void addInterestAmount(@SuppressWarnings("unused") Money principalDue) {
        return;
    }
    @Override
    public Set<LoanInterestRecalcualtionAdditionalDetails> getLoanCompoundingDetails() {
        return null;
    }
    @Override
    public void setEMIFixedSpecificToInstallmentTrue() {
        this.isEMIFixedSpecificToInstallment = true;
    }
    @Override
    public boolean isEMIFixedSpecificToInstallment() {
        return isEMIFixedSpecificToInstallment;
    }
    @Override
    public BigDecimal rescheduleInterestPortion() {
        return null;
    }
    @Override
    public void setRescheduleInterestPortion(BigDecimal rescheduleInterestPortion) {
        return;
    }
}
