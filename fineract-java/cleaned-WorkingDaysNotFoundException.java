
package org.apache.fineract.organisation.workingdays.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class WorkingDaysNotFoundException extends AbstractPlatformResourceNotFoundException {
    public WorkingDaysNotFoundException() {
        super("error.msg.working.days.not.configured", "Must configure the Working days for the organisation.");
    }
    public WorkingDaysNotFoundException(EmptyResultDataAccessException e) {
        super("error.msg.working.days.not.configured", "Must configure the Working days for the organisation.", e);
    }
}
