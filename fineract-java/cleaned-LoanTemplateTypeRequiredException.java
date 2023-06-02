
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanTemplateTypeRequiredException extends AbstractPlatformDomainRuleException {
    public LoanTemplateTypeRequiredException(final String defaultUserMessage) {
        super("error.msg.loan.template.type.required", defaultUserMessage);
    }
}
