
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.springframework.stereotype.Component;
@Component
public class AprCalculator {
    public BigDecimal calculateFrom(final PeriodFrequencyType interestPeriodFrequencyType, final BigDecimal interestRatePerPeriod,
            final Integer numberOfRepayments, final Integer repaymentEvery, final PeriodFrequencyType repaymentPeriodFrequencyType) {
        BigDecimal defaultAnnualNominalInterestRate = BigDecimal.ZERO;
        switch (interestPeriodFrequencyType) {
            case DAYS:
                defaultAnnualNominalInterestRate = interestRatePerPeriod.multiply(BigDecimal.valueOf(365));
            break;
            case WEEKS:
                defaultAnnualNominalInterestRate = interestRatePerPeriod.multiply(BigDecimal.valueOf(52));
            break;
            case MONTHS:
                defaultAnnualNominalInterestRate = interestRatePerPeriod.multiply(BigDecimal.valueOf(12));
            break;
            case YEARS:
                defaultAnnualNominalInterestRate = interestRatePerPeriod.multiply(BigDecimal.valueOf(1));
            break;
            case WHOLE_TERM:
                final BigDecimal ratePerPeriod = interestRatePerPeriod.divide(BigDecimal.valueOf(numberOfRepayments * repaymentEvery), 8,
                        RoundingMode.HALF_UP);
                switch (repaymentPeriodFrequencyType) {
                    case DAYS:
                        defaultAnnualNominalInterestRate = ratePerPeriod.multiply(BigDecimal.valueOf(365));
                    break;
                    case WEEKS:
                        defaultAnnualNominalInterestRate = ratePerPeriod.multiply(BigDecimal.valueOf(52));
                    break;
                    case MONTHS:
                        defaultAnnualNominalInterestRate = ratePerPeriod.multiply(BigDecimal.valueOf(12));
                    break;
                    case YEARS:
                        defaultAnnualNominalInterestRate = ratePerPeriod.multiply(BigDecimal.valueOf(1));
                    break;
                    case WHOLE_TERM:
                    break;
                    case INVALID:
                    break;
                }
            break;
            case INVALID:
            break;
        }
        return defaultAnnualNominalInterestRate;
    }
}
