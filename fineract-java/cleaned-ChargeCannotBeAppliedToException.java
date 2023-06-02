
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ChargeCannotBeAppliedToException extends AbstractPlatformDomainRuleException {
    public ChargeCannotBeAppliedToException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.charge.cannot.be.applied.to" + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
