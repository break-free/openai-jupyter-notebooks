
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanCreatedBusinessEvent extends LoanBusinessEvent {
    public LoanCreatedBusinessEvent(Loan value) {
        super(value);
    }
}
