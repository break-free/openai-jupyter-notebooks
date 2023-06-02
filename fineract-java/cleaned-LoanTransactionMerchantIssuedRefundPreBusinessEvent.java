
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.businessevent.domain.loan.LoanBusinessEvent;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanTransactionMerchantIssuedRefundPreBusinessEvent extends LoanBusinessEvent {
    public LoanTransactionMerchantIssuedRefundPreBusinessEvent(Loan value) {
        super(value);
    }
}
