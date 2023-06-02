
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanApplicationNotInSubmittedAndPendingApprovalStateCannotBeModified extends AbstractPlatformDomainRuleException {
    public LoanApplicationNotInSubmittedAndPendingApprovalStateCannotBeModified(final Long id) {
        super("error.msg.loan.cannot.modify.loan.in.its.present.state",
                "Loan application with identifier " + id + " cannot be modified in its current state.", id);
    }
}
