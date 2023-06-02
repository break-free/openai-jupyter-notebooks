
package org.apache.fineract.portfolio.charge.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanChargeCannotBeWaivedException extends AbstractPlatformDomainRuleException {
    public enum LoanChargeCannotBeWaivedReason {
        ALREADY_PAID, ALREADY_WAIVED, LOAN_INACTIVE, WAIVE_NOT_ALLOWED_FOR_CHARGE;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("ALREADY_PAID")) {
                return "This loan charge has been completely paid";
            } else if (name().toString().equalsIgnoreCase("ALREADY_WAIVED")) {
                return "This loan charge has already been waived";
            } else if (name().toString().equalsIgnoreCase("LOAN_INACTIVE")) {
                return "This loan charge can be waived as the loan associated with it is currently inactive";
            } else if (name().toString().equalsIgnoreCase("WAIVE_NOT_ALLOWED_FOR_CHARGE")) {
                return "This loan charge can be waived";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("ALREADY_PAID")) {
                return "error.msg.loan.charge.already.paid";
            } else if (name().toString().equalsIgnoreCase("ALREADY_WAIVED")) {
                return "error.msg.loan.charge.already.waived";
            } else if (name().toString().equalsIgnoreCase("LOAN_INACTIVE")) {
                return "error.msg.loan.charge.associated.loan.inactive";
            } else if (name().toString().equalsIgnoreCase("WAIVE_NOT_ALLOWED_FOR_CHARGE")) {
                return "error.msg.loan.charge.waive.not.allowed";
            }
            return name().toString();
        }
    }
    public LoanChargeCannotBeWaivedException(final LoanChargeCannotBeWaivedReason reason, final Long loanChargeId) {
        super(reason.errorCode(), reason.errorMessage(), loanChargeId);
    }
}
