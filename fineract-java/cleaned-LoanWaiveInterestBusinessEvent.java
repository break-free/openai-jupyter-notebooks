
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanWaiveInterestBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanWaiveInterestBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
