
package org.apache.fineract.portfolio.loanaccount.guarantor.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class GuarantorNotFoundException extends AbstractPlatformResourceNotFoundException {
    public GuarantorNotFoundException(final Long id) {
        super("error.msg.loan.guarantor.not.found", "Guarantor with identifier " + id + " does not exist", id);
    }
    public GuarantorNotFoundException(final Long loanId, final Long guarantorId) {
        super("error.msg.loan.guarantor.not.found",
                "Guarantor with identifier " + guarantorId + " does not exist for loan with Identifier " + loanId, loanId, guarantorId);
    }
    public GuarantorNotFoundException(final Long loanId, final Long guarantorId, final Long guarantorFundingId) {
        super("error.msg.loan.guarantor.not.found", "Guarantor with identifier " + guarantorId + "and with funding detail "
                + guarantorFundingId + " does not exist for loan with Identifier " + loanId, loanId, guarantorId, guarantorFundingId);
    }
}
