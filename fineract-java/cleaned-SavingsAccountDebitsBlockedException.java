
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsAccountDebitsBlockedException extends AbstractPlatformDomainRuleException {
    public SavingsAccountDebitsBlockedException(final Long accountId) {
        super("error.msg.savings.account.debit.transaction.not.allowed",
                "Any debit transactions from " + accountId + " is not allowed, since the account is blocked for debits", accountId);
    }
}
