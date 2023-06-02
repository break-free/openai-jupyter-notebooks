
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class GroupLevelNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GroupLevelNotFoundException(final Long id) {
        super("error.msg.group.level.id.invalid", "Group level with identifier " + id + " does not exist", id);
    }
}
