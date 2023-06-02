
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class CenterNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CenterNotFoundException(final Long id) {
        super("error.msg.center.id.invalid", "Center with identifier " + id + " does not exist", id);
    }
    public CenterNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.center.id.invalid", "Center with identifier " + id + " does not exist", id, e);
    }
}
