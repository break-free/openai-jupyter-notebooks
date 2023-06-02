
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidLoanTransactionTypeException extends AbstractPlatformDomainRuleException {
    public InvalidLoanTransactionTypeException(final String action, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.loan." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
