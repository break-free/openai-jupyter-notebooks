
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanChargePaymentPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanChargePaymentPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
