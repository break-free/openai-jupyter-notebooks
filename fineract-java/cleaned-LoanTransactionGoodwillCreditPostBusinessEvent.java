
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanTransactionGoodwillCreditPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanTransactionGoodwillCreditPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
