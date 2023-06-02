
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanUndoDisbursalBusinessEvent extends LoanBusinessEvent {
    public LoanUndoDisbursalBusinessEvent(Loan value) {
        super(value);
    }
}
