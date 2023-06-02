
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanInterestRecalculationBusinessEvent extends LoanBusinessEvent {
    public LoanInterestRecalculationBusinessEvent(Loan value) {
        super(value);
    }
}
