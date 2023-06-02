
package org.apache.fineract.portfolio.collateral.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CollateralCannotBeCreatedException extends AbstractPlatformDomainRuleException {
    public enum LoanCollateralCannotBeCreatedReason {
        LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE")) {
                return "This collateral cannot be created as the loan it is associated with is not in submitted and pending approval stage";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE")) {
                return "error.msg.loan.collateral.associated.loan.not.in.submitted.and.pending.approval.stage";
            }
            return name().toString();
        }
    }
    public CollateralCannotBeCreatedException(final LoanCollateralCannotBeCreatedReason reason, final Long loanId) {
        super(reason.errorCode(), reason.errorMessage(), loanId);
    }
}
