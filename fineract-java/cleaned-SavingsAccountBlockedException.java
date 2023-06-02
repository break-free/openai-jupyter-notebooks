
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsAccountBlockedException extends AbstractPlatformDomainRuleException {
    public SavingsAccountBlockedException(final Long accountId) {
        super("error.msg.saving.account.blocked.transaction.not.allowed",
                "Any transaction to " + accountId + " is not allowed, since the account is blocked", accountId);
    }
}
