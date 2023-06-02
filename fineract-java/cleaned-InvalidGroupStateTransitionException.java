
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidGroupStateTransitionException extends AbstractPlatformDomainRuleException {
    public InvalidGroupStateTransitionException(final String levelName, final String action, final String postFix,
            final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg." + levelName + "." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
