
package org.apache.fineract.organisation.monetary.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class OrganizationalCurrencyNotFoundException extends AbstractPlatformResourceNotFoundException {
    public OrganizationalCurrencyNotFoundException(final String currencyCode) {
        super("error.msg.currency.currencyCode.invalid.or.not.supported",
                "Currency with identifier " + currencyCode + " does not exist or is not supported by the Organization", currencyCode);
    }
}
