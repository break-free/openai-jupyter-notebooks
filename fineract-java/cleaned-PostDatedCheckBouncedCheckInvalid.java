
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class PostDatedCheckBouncedCheckInvalid extends AbstractPlatformDomainRuleException {
    public PostDatedCheckBouncedCheckInvalid(final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.post.dated.checkNo..invalid", defaultUserMessage, defaultUserMessageArgs);
    }
}
