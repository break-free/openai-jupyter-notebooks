
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidLoanTypeException extends AbstractPlatformDomainRuleException {
    public InvalidLoanTypeException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loan.type.invalid", defaultUserMessage, defaultUserMessageArgs);
    }
}
