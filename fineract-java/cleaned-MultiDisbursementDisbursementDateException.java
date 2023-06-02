
package org.apache.fineract.portfolio.loanaccount.loanschedule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MultiDisbursementDisbursementDateException extends AbstractPlatformDomainRuleException {
    public MultiDisbursementDisbursementDateException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loanschedule.out.of.schedule.dusbursement.date", defaultUserMessage, defaultUserMessageArgs);
    }
}
