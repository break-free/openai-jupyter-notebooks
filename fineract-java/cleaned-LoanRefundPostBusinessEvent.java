
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanRefundPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanRefundPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
