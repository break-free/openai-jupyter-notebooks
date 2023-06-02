
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CalendarParameterUpdateNotSupportedException extends AbstractPlatformDomainRuleException {
    public CalendarParameterUpdateNotSupportedException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.calendar.update.of." + postFix + ".is.not.supported", defaultUserMessage, defaultUserMessageArgs);
    }
}
