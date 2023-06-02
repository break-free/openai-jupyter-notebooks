
package org.apache.fineract.portfolio.collateralmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class LoanCollateralManagementNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanCollateralManagementNotFoundException(final Long id) {
        super("error.msg.loan.collateral.id.invalid", "Loan Collateral with identifier " + id + " does not exist", id);
    }
}
