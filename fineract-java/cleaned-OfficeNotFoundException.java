
package org.apache.fineract.organisation.office.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class OfficeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public OfficeNotFoundException(final Long id) {
        super("error.msg.office.id.invalid", "Office with identifier " + id + " does not exist", id);
    }
    public OfficeNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.office.id.invalid", "Office with identifier " + id + " does not exist", id, e);
    }
}
