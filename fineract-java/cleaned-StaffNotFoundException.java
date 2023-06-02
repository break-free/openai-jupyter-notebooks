
package org.apache.fineract.organisation.staff.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class StaffNotFoundException extends AbstractPlatformResourceNotFoundException {
    public StaffNotFoundException(final Long id) {
        super("error.msg.staff.id.invalid", "Staff with identifier " + id + " does not exist", id);
    }
    public StaffNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.staff.id.invalid", "Staff with identifier " + id + " does not exist", id, e);
    }
}
