
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanApprovedBusinessEvent extends LoanBusinessEvent {
    public LoanApprovedBusinessEvent(Loan value) {
        super(value);
    }
}
