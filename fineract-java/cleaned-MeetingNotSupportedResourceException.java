
package org.apache.fineract.portfolio.meeting.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MeetingNotSupportedResourceException extends AbstractPlatformDomainRuleException {
    public MeetingNotSupportedResourceException(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.meeting.not.supported.resource", defaultUserMessage, defaultUserMessageArgs);
    }
}
