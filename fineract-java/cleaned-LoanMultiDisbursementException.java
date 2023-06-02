
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanMultiDisbursementException extends AbstractPlatformDomainRuleException {
    public LoanMultiDisbursementException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg." + defaultUserMessage, defaultUserMessage, defaultUserMessageArgs);
    }
}
