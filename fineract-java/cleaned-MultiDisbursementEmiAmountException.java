
package org.apache.fineract.portfolio.loanaccount.loanschedule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MultiDisbursementEmiAmountException extends AbstractPlatformDomainRuleException {
    public MultiDisbursementEmiAmountException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loanschedule.emi.amount.must.be.greter.than.interest", defaultUserMessage, defaultUserMessageArgs);
    }
}
