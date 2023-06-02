
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GroupAccountExistsException extends AbstractPlatformDomainRuleException {
    public GroupAccountExistsException(final String action, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.group." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
