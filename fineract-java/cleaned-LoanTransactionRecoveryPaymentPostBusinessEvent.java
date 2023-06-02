
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanTransactionRecoveryPaymentPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanTransactionRecoveryPaymentPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
