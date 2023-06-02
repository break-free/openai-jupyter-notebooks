
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanTransactionPayoutRefundPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanTransactionPayoutRefundPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
