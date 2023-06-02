
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GroupMustBePendingToBeDeletedException extends AbstractPlatformDomainRuleException {
    public GroupMustBePendingToBeDeletedException(final Long id) {
        super("error.msg.group.cannot.be.deleted", "Group with identifier " + id + " cannot be deleted as it is not in `Pending` state.",
                id);
    }
}
