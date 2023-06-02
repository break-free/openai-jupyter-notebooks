
package org.apache.fineract.portfolio.meeting.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MeetingDateException extends AbstractPlatformDomainRuleException {
    public MeetingDateException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.meeting.date." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
