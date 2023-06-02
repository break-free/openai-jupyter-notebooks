
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanApplicationDateException extends AbstractPlatformDomainRuleException {
    public LoanApplicationDateException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loan.application." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
