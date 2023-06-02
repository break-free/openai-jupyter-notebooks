
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanCreditBalanceRefundPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanCreditBalanceRefundPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
