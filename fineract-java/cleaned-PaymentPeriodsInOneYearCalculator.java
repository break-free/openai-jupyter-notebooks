
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.time.LocalDate;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
public interface PaymentPeriodsInOneYearCalculator {
    Integer calculate(PeriodFrequencyType repaymentFrequencyType);
    double calculatePortionOfRepaymentPeriodInterestChargingGrace(LocalDate repaymentPeriodStartDate, LocalDate scheduledDueDate,
            LocalDate interestChargedFromLocalDate, PeriodFrequencyType repaymentPeriodFrequencyType, Integer repaidEvery);
}
