
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.loanaccount.domain.LoanInterestRecalcualtionAdditionalDetails;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanSchedulePeriodData;
public interface LoanScheduleModelPeriod {
    LoanSchedulePeriodData toData();
    boolean isRepaymentPeriod();
    Integer periodNumber();
    LocalDate periodFromDate();
    LocalDate periodDueDate();
    BigDecimal principalDue();
    BigDecimal interestDue();
    BigDecimal feeChargesDue();
    BigDecimal penaltyChargesDue();
    void addLoanCharges(BigDecimal feeCharge, BigDecimal penaltyCharge);
    boolean isRecalculatedInterestComponent();
    void addPrincipalAmount(Money principalDue);
    void addInterestAmount(Money interestDue);
    Set<LoanInterestRecalcualtionAdditionalDetails> getLoanCompoundingDetails();
    void setEMIFixedSpecificToInstallmentTrue();
    boolean isEMIFixedSpecificToInstallment();
    BigDecimal rescheduleInterestPortion();
    void setRescheduleInterestPortion(BigDecimal rescheduleInterestPortion);
}
