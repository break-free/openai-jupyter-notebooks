
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SavingsAccountChargeWithoutMandatoryFieldException extends AbstractPlatformDomainRuleException {
    public SavingsAccountChargeWithoutMandatoryFieldException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + "." + postFix + ".cannot.be.blank", defaultUserMessage, defaultUserMessageArgs);
    }
}
