
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanTransactionMerchantIssuedRefundPostBusinessEvent extends LoanTransactionBusinessEvent {
    public LoanTransactionMerchantIssuedRefundPostBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
