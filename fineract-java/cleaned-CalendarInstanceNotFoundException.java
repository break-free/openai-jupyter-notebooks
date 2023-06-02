
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CalendarInstanceNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CalendarInstanceNotFoundException(final Long id) {
        super("error.msg.calendar.instance.id.invalid", "Calendar Instance with identifier " + id + " does not exist", id);
    }
    public CalendarInstanceNotFoundException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.calendar.instance." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
