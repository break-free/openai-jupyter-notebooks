
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanChargeCannotBeUpdatedException extends AbstractPlatformDomainRuleException {
    public enum LoanChargeCannotBeUpdatedReason {
        ALREADY_PAID, ALREADY_WAIVED, LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("ALREADY_PAID")) {
                return "This loan charge has been partially/completely paid";
            } else if (name().toString().equalsIgnoreCase("ALREADY_WAIVED")) {
                return "This loan charge has already been waived";
            } else if (name().toString().equalsIgnoreCase("LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE")) {
                return "This charge cannot be updated as the loan it is associated with is not in submitted and pending approval stage";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("ALREADY_PAID")) {
                return "error.msg.loan.charge.already.paid";
            } else if (name().toString().equalsIgnoreCase("ALREADY_WAIVED")) {
                return "error.msg.loan.charge.already.waived";
            } else if (name().toString().equalsIgnoreCase("LOAN_NOT_IN_SUBMITTED_AND_PENDING_APPROVAL_STAGE")) {
                return "error.msg.loan.charge.associated.loan.not.in.submitted.and.pending.approval.stage";
            }
            return name().toString();
        }
    }
    public LoanChargeCannotBeUpdatedException(final LoanChargeCannotBeUpdatedReason reason, final Long loanChargeId) {
        super(reason.errorCode(), reason.errorMessage(), loanChargeId);
    }
}
