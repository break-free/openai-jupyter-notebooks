
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanUndoLastDisbursalBusinessEvent extends LoanBusinessEvent {
    public LoanUndoLastDisbursalBusinessEvent(Loan value) {
        super(value);
    }
}
