
package org.apache.fineract.adhocquery.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class AdHocNotFoundException extends AbstractPlatformResourceNotFoundException {
    public AdHocNotFoundException(final Long id) {
        super("error.msg.adhocquery.adhoc.id.invalid", "Adhoc Record with identifier " + id + " does not exist", id);
    }
    public AdHocNotFoundException(final Long id, EmptyResultDataAccessException e) {
        super("error.msg.adhocquery.adhoc.id.invalid", "Adhoc Record with identifier " + id + " does not exist", id, e);
    }
}
