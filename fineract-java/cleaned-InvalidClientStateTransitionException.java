
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidClientStateTransitionException extends AbstractPlatformDomainRuleException {
    public InvalidClientStateTransitionException(final String action, final String postFix, final String defaultUserMessage,
            final Object... defaultUserMessageArgs) {
        super("error.msg.client." + action + "." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
