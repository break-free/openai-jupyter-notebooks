
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanRemoveOfficerBusinessEvent extends LoanBusinessEvent {
    public LoanRemoveOfficerBusinessEvent(Loan value) {
        super(value);
    }
}
