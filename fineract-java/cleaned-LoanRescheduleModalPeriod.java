
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.LoanSchedulePeriodData;
public interface LoanRescheduleModalPeriod {
    LoanSchedulePeriodData toData();
    Integer periodNumber();
    Integer oldPeriodNumber();
    LocalDate periodFromDate();
    LocalDate periodDueDate();
    BigDecimal principalDue();
    BigDecimal interestDue();
    BigDecimal feeChargesDue();
    BigDecimal penaltyChargesDue();
    boolean isNew();
}
