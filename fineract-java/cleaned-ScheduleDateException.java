
package org.apache.fineract.portfolio.loanaccount.loanschedule.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ScheduleDateException extends AbstractPlatformDomainRuleException {
    public ScheduleDateException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.loanschedule.schedule.due.date.not.valid", defaultUserMessage, defaultUserMessageArgs);
    }
}
