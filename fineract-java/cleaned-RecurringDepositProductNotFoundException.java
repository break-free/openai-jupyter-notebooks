
package org.apache.fineract.portfolio.savings.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class RecurringDepositProductNotFoundException extends AbstractPlatformResourceNotFoundException {
    public RecurringDepositProductNotFoundException(final Long id) {
        super("error.msg.recurringdepositproduct.id.invalid", "Recurring deposit product with identifier " + id + " does not exist", id);
    }
}
