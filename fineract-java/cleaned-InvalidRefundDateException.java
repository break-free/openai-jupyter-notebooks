
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidRefundDateException extends AbstractPlatformDomainRuleException {
    public InvalidRefundDateException(final String refundDateAsString) {
        super("error.msg.loan.refund.failed",
                "The refund date`" + refundDateAsString + "`" + "` cannot be before the smallest repayment transaction date",
                new Object[] { refundDateAsString });
    }
    public InvalidRefundDateException(final String defaultUserMessage, final String entity, final Object... defaultUserMessageArgs) {
        super("error.msg.loan." + entity, defaultUserMessage, defaultUserMessageArgs);
    }
}
