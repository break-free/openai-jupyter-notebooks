
package org.apache.fineract.organisation.holiday.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class HolidayNotFoundException extends AbstractPlatformResourceNotFoundException {
    public HolidayNotFoundException(final Long id) {
        super("error.msg.holiday.id.invalid", "Holiday with identifier " + id + " does not exist", id);
    }
    public HolidayNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.holiday.id.invalid", "Holiday with identifier " + id + " does not exist", id, e);
    }
}
