
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanDisbursalBusinessEvent extends LoanBusinessEvent {
    public LoanDisbursalBusinessEvent(Loan value) {
        super(value);
    }
}
