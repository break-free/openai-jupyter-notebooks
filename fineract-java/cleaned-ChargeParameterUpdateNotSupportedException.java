
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ChargeParameterUpdateNotSupportedException extends AbstractPlatformDomainRuleException {
    public ChargeParameterUpdateNotSupportedException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.charge.update.of." + postFix + ".is.not.supported", defaultUserMessage, defaultUserMessageArgs);
    }
}
