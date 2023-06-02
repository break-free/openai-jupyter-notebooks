
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanApplicationNotInSubmittedAndPendingApprovalStateCannotBeDeleted extends AbstractPlatformDomainRuleException {
    public LoanApplicationNotInSubmittedAndPendingApprovalStateCannotBeDeleted(final Long id) {
        super("error.msg.loan.cannot.delete.loan.in.its.present.state",
                "Loan with identifier " + id + " cannot be deleted in its current state.", id);
    }
}
