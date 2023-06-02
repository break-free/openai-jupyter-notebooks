
package org.apache.fineract.portfolio.savings.domain.interest;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.domain.LocalDateInterval;
import org.apache.fineract.portfolio.savings.SavingsCompoundingInterestPeriodType;
import org.apache.fineract.portfolio.savings.SavingsInterestCalculationType;
public interface CompoundingPeriod {
    BigDecimal calculateInterest(SavingsCompoundingInterestPeriodType compoundingInterestPeriodType,
            SavingsInterestCalculationType interestCalculationType, BigDecimal interestFromPreviousPostingPeriod,
            BigDecimal interestRateAsFraction, long daysInYear, BigDecimal minBalanceForInterestCalculation,
            BigDecimal overdraftInterestRateAsFraction, BigDecimal minOverdraftForInterestCalculation);
    LocalDateInterval getPeriodInterval();
}
