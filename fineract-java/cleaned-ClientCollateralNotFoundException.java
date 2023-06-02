
package org.apache.fineract.portfolio.collateralmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ClientCollateralNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ClientCollateralNotFoundException(final Long id) {
        super("error.msg.client.collateral.id.invalid", "Collateral with identifier " + id + " does not exist", id);
    }
}
