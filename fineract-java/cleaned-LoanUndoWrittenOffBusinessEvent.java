
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanUndoWrittenOffBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanUndoWrittenOffBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
