
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InvalidDateInputException extends AbstractPlatformResourceNotFoundException {
    public InvalidDateInputException(final String startDate, final String endDate) {
        super("error.msg.invalid.endDate", "EndDate " + endDate + " cannot be before StartDate " + startDate);
    }
}
