
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanRejectTransferBusinessEvent extends LoanBusinessEvent {
    public LoanRejectTransferBusinessEvent(Loan value) {
        super(value);
    }
}
