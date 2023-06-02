
package org.apache.fineract.portfolio.businessevent.domain.loan;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public abstract class LoanBusinessEvent extends AbstractBusinessEvent<Loan> {
    public LoanBusinessEvent(Loan value) {
        super(value);
    }
}
