
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanInitiateTransferBusinessEvent extends LoanBusinessEvent {
    public LoanInitiateTransferBusinessEvent(Loan value) {
        super(value);
    }
}
