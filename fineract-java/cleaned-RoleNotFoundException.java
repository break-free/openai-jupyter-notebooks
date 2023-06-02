
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class RoleNotFoundException extends AbstractPlatformResourceNotFoundException {
    public RoleNotFoundException(final Long id) {
        super("error.msg.role.id.invalid", "Role with identifier " + id + " does not exist", id);
    }
    public RoleNotFoundException(final String name) {
        super("error.msg.role.name.invalid", "Role with name " + name + " does not exist", name);
    }
    public RoleNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.role.id.invalid", "Role with identifier " + id + " does not exist", id, e);
    }
}
