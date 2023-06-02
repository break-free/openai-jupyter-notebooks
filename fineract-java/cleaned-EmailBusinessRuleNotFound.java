
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class EmailBusinessRuleNotFound extends AbstractPlatformResourceNotFoundException {
    public EmailBusinessRuleNotFound(final Long resourceId) {
        super("error.msg.email.business.rule.not.found", "Email business rule with identifier `" + resourceId + "` does not exist",
                resourceId);
    }
    public EmailBusinessRuleNotFound(final Long resourceId, IndexOutOfBoundsException e) {
        super("error.msg.email.business.rule.not.found", "Email business rule with identifier `" + resourceId + "` does not exist",
                resourceId, e);
    }
}
