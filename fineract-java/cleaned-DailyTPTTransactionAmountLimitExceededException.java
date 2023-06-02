
package org.apache.fineract.portfolio.self.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DailyTPTTransactionAmountLimitExceededException extends AbstractPlatformDomainRuleException {
    public DailyTPTTransactionAmountLimitExceededException(Long accountId, Integer accountType) {
        super("error.msg.beneficiary.daily.tpt.transfer.limit.for.fromaccountid." + accountId + ".fromaccounttype." + accountType
                + ".exceeded", "Daily third party transfer limit for the source account excceeded");
    }
}
