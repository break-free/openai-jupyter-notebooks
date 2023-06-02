
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class NotValidRecurringDateException extends AbstractPlatformDomainRuleException {
    public NotValidRecurringDateException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.calendar." + postFix + ".not.valid.recurring.date", defaultUserMessage, defaultUserMessageArgs);
    }
}
