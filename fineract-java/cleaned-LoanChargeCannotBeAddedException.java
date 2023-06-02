
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanChargeCannotBeAddedException extends AbstractPlatformDomainRuleException {
    public LoanChargeCannotBeAddedException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + ".cannot.be.added.as." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
