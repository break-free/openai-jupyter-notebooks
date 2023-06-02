
package org.apache.fineract.organisation.monetary.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CurrencyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CurrencyNotFoundException(final String currencyCode) {
        super("error.msg.currency.currencyCode.invalid", "Currency with identifier " + currencyCode + " does not exist", currencyCode);
    }
}
