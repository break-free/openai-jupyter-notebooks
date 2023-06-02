
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class NotSupportedLoanTemplateTypeException extends AbstractPlatformDomainRuleException {
    public NotSupportedLoanTemplateTypeException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loan.template.type.not.supported", defaultUserMessage, defaultUserMessageArgs);
    }
}
