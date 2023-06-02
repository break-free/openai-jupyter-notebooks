
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class GroupNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GroupNotFoundException(final Long id) {
        super("error.msg.group.id.invalid", "Group with identifier " + id + " does not exist", id);
    }
    public GroupNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.group.id.invalid", "Group with identifier " + id + " does not exist", id, e);
    }
}
