
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanCloseAsRescheduleBusinessEvent extends LoanBusinessEvent {
    public LoanCloseAsRescheduleBusinessEvent(Loan value) {
        super(value);
    }
}
