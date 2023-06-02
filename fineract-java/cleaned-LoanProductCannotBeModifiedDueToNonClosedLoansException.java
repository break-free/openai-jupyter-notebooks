
package org.apache.fineract.portfolio.loanproduct.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class LoanProductCannotBeModifiedDueToNonClosedLoansException extends AbstractPlatformDomainRuleException {
    public LoanProductCannotBeModifiedDueToNonClosedLoansException(final Long id) {
        super("error.msg.loanproduct.not.modifiable.due.to.non.closed.loans",
                "Loan product with identifier " + id + " cannot be modified due to non closed loans associated with it.", id);
    }
}
