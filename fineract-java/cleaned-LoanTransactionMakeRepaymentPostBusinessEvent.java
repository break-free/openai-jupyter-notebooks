
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanTransactionMakeRepaymentPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanTransactionMakeRepaymentPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
