
package org.apache.fineract.portfolio.tax.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TaxComponentNotFoundException extends AbstractPlatformResourceNotFoundException {
    public TaxComponentNotFoundException(final Long id) {
        super("error.msg.tax.component.id.invalid", "tax component with identifier " + id + " does not exist", id);
    }
}
