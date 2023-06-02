
package org.apache.fineract.portfolio.savings.domain.interest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
public class CompoundInterestHelper {
    public Money calculateInterestForAllPostingPeriods(final MonetaryCurrency currency, final List<PostingPeriod> allPeriods,
            LocalDate lockUntil, Boolean interestTransferEnabled) {
        Money interestEarned = Money.zero(currency);
        BigDecimal compoundedInterest = BigDecimal.ZERO;
        BigDecimal unCompoundedInterest = BigDecimal.ZERO;
        final CompoundInterestValues compoundInterestValues = new CompoundInterestValues(compoundedInterest, unCompoundedInterest);
        for (final PostingPeriod postingPeriod : allPeriods) {
            final BigDecimal interestEarnedThisPeriod = postingPeriod.calculateInterest(compoundInterestValues);
            final Money moneyToBePostedForPeriod = Money.of(currency, interestEarnedThisPeriod);
            interestEarned = interestEarned.plus(moneyToBePostedForPeriod);
            if (!(postingPeriod.isInterestTransfered() || !interestTransferEnabled
                    || (lockUntil != null && !postingPeriod.dateOfPostingTransaction().isAfter(lockUntil)))) {
                compoundInterestValues.setcompoundedInterest(BigDecimal.ZERO);
            }
        }
        return interestEarned;
    }
}
