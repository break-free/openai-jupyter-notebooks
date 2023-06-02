
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CalendarDateException extends AbstractPlatformDomainRuleException {
    public CalendarDateException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.calendar." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
