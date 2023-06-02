
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanUndoApprovalBusinessEvent extends LoanBusinessEvent {
    public LoanUndoApprovalBusinessEvent(Loan value) {
        super(value);
    }
}
