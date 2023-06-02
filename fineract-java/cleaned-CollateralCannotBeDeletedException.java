
package org.apache.fineract.portfolio.collateralmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CollateralCannotBeDeletedException extends AbstractPlatformDomainRuleException {
    public enum CollateralCannotBeDeletedReason {
        COLLATERAL_IS_ALREADY_ATTACHED;
        public String errorMessage() {
            if (name().equalsIgnoreCase("COLLATERAL_IS_ALREADY_ATTACHED")) {
                return "This collateral cannot be deleted as this is associated with one or more client collaterals";
            }
            return name();
        }
        public String errorCode() {
            if (name().equalsIgnoreCase("COLLATERAL_IS_ALREADY_ATTACHED")) {
                return "error.msg.collateral.is.already.associated.with.client.collateral";
            }
            return name();
        }
    }
    public CollateralCannotBeDeletedException(final CollateralCannotBeDeletedReason reason, final Long loanCollateralId) {
        super(reason.errorCode(), reason.errorMessage(), loanCollateralId);
    }
}
