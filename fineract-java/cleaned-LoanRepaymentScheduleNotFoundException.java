
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class LoanRepaymentScheduleNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanRepaymentScheduleNotFoundException(final Integer id) {
        super("error.msg.loan.repayment.installment.id.invalid", "Loan repayment with installment identifier " + id + " does not exist",
                id);
    }
    public LoanRepaymentScheduleNotFoundException(final Long id) {
        super("error.msg.loan.repayment.id.invalid", "Loan repayment with identifier " + id + " does not exist", id);
    }
    public LoanRepaymentScheduleNotFoundException(final Long id, final Integer loan) {
        super("error.msg.loan.id" + id + ".not.contains.repayment.schedule", "Loan does not have repayment scheduled", loan);
    }
}
