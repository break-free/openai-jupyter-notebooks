
package org.apache.fineract.portfolio.tax.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TaxMappingNotFoundException extends AbstractPlatformResourceNotFoundException {
    public TaxMappingNotFoundException(final Long id) {
        super("error.msg.tax.group.id.invalid", "Tax group mapping with identifier " + id + " does not exist", id);
    }
}
