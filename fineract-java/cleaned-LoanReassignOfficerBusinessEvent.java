
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanReassignOfficerBusinessEvent extends LoanBusinessEvent {
    public LoanReassignOfficerBusinessEvent(Loan value) {
        super(value);
    }
}
