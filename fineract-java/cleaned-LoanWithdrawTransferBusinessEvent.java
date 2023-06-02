
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanWithdrawTransferBusinessEvent extends LoanBusinessEvent {
    public LoanWithdrawTransferBusinessEvent(Loan value) {
        super(value);
    }
}
