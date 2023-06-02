
package org.apache.fineract.portfolio.tax.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TaxGroupNotFoundException extends AbstractPlatformResourceNotFoundException {
    public TaxGroupNotFoundException(final Long id) {
        super("error.msg.tax.group.id.invalid", "tax group with identifier " + id + " does not exist", id);
    }
}
