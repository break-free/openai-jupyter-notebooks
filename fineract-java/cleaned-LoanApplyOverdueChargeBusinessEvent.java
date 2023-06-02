
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanApplyOverdueChargeBusinessEvent extends LoanBusinessEvent {
    public LoanApplyOverdueChargeBusinessEvent(Loan value) {
        super(value);
    }
}
