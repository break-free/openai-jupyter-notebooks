
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class GroupHasNoStaffException extends AbstractPlatformResourceNotFoundException {
    public GroupHasNoStaffException(final Long groupId) {
        super("error.msg.group.has.no.staff", "Group with identifier " + groupId + " does not have staff", groupId);
    }
}
