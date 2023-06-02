
package org.apache.fineract.portfolio.loanaccount.loanschedule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MultiDisbursementOutstandingAmoutException extends AbstractPlatformDomainRuleException {
    public MultiDisbursementOutstandingAmoutException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loanschedule.exceeding.max.outstanding.balance", defaultUserMessage, defaultUserMessageArgs);
    }
}
