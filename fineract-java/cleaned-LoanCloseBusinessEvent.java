
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanCloseBusinessEvent extends LoanBusinessEvent {
    public LoanCloseBusinessEvent(Loan value) {
        super(value);
    }
}
