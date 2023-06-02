
package org.apache.fineract.organisation.teller.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CashierNotFoundException extends AbstractPlatformResourceNotFoundException {
    private static final String ERROR_MESSAGE_CODE = "error.msg.cashier.not.found";
    private static final String DEFAULT_ERROR_MESSAGE = "Cashier with identifier {0,number,long} not found!";
    public CashierNotFoundException(Long cashierId) {
        super(ERROR_MESSAGE_CODE, DEFAULT_ERROR_MESSAGE, cashierId);
    }
}
