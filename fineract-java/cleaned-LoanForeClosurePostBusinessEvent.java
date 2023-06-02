
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanForeClosurePostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanForeClosurePostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
