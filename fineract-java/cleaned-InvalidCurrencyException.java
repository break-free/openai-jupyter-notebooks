
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidCurrencyException extends AbstractPlatformDomainRuleException {
    public InvalidCurrencyException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + "." + postFix + ".invalid.currency", defaultUserMessage, defaultUserMessageArgs);
    }
}
