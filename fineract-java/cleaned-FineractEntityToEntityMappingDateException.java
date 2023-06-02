
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class FineractEntityToEntityMappingDateException extends AbstractPlatformResourceNotFoundException {
    public FineractEntityToEntityMappingDateException(final String startDate, final String endDate) {
        super("error.msg.invalid.endDate", "EndDate " + endDate + " cannot be before StartDate " + startDate);
    }
}
