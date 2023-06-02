
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanRejectedBusinessEvent extends LoanBusinessEvent {
    public LoanRejectedBusinessEvent(Loan value) {
        super(value);
    }
}
