
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanWrittenOffPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanWrittenOffPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
