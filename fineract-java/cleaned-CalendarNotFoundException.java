
package org.apache.fineract.portfolio.calendar.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class CalendarNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CalendarNotFoundException(final Long id) {
        super("error.msg.calendar.id.invalid", "Calendar with identifier " + id + " does not exist", id);
    }
    public CalendarNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.calendar.id.invalid", "Calendar with identifier " + id + " does not exist", id, e);
    }
}
