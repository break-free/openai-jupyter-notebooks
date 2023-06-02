
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidPaidInAdvanceAmountException extends AbstractPlatformDomainRuleException {
    public InvalidPaidInAdvanceAmountException(final String refundAmountString) {
        super("error.msg.loan.refund.amount.invalid",
                "The refund amount `" + refundAmountString + "`" + "` is invalid or loan is not paid in advance.",
                new Object[] { refundAmountString });
    }
}
