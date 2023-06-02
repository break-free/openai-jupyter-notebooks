
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MeetingFrequencyMismatchException extends AbstractPlatformDomainRuleException {
    public MeetingFrequencyMismatchException(final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.calendar." + postFix + ".not.the.same.as.meeting.frequency", defaultUserMessage, defaultUserMessageArgs);
    }
}
