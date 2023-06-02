
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class GroupRoleNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GroupRoleNotFoundException(final Long id) {
        super("error.msg.group.role.id.invalid", "Group Role with identifier " + id + " does not exist", id);
    }
    public GroupRoleNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.group.role.id.invalid", "Group Role with identifier " + id + " does not exist", id, e);
    }
}
