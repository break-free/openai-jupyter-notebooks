
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsAccountCreditsBlockedException extends AbstractPlatformDomainRuleException {
    public SavingsAccountCreditsBlockedException(final Long accountId) {
        super("error.msg.savings.account.credit.transaction.not.allowed",
                "Any Credit transactions to " + accountId + " is not allowed, since the account is blocked for credits", accountId);
    }
}
