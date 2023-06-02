
package org.apache.fineract.portfolio.loanaccount.guarantor.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DuplicateGuarantorException extends AbstractPlatformDomainRuleException {
    public DuplicateGuarantorException(final String action, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
