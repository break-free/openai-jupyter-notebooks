
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanChargeWithoutMandatoryFieldException extends AbstractPlatformDomainRuleException {
    public LoanChargeWithoutMandatoryFieldException(final String entity, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + entity + "." + postFix + ".cannot.be.blank", defaultUserMessage, defaultUserMessageArgs);
    }
}
