
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class NotInMinMaxRangeException extends AbstractPlatformDomainRuleException {
    public NotInMinMaxRangeException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("validation.msg." + entity + "." + postFix + ".is.not.within.min.max.range", defaultUserMessage, defaultUserMessageArgs);
    }
}
