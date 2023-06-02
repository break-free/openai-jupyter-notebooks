
package org.apache.fineract.portfolio.paymenttype.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class PaymentTypeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public PaymentTypeNotFoundException(final Long id) {
        super("error.msg.payment.type.invalid", "PaymentType with " + id + " does not exist", id);
    }
}
