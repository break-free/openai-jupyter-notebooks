
package org.apache.fineract.portfolio.collateralmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CollateralNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CollateralNotFoundException(final Long id) {
        super("error.msg.collateral.id.invalid", "Collateral with identifier " + id + " does not exist", id);
    }
}
