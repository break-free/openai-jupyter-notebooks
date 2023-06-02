
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanAcceptTransferBusinessEvent extends LoanBusinessEvent {
    public LoanAcceptTransferBusinessEvent(Loan value) {
        super(value);
    }
}
