
package org.apache.fineract.portfolio.businessevent.domain.loan.transaction;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public abstract class LoanTransactionBusinessEvent extends AbstractBusinessEvent<LoanTransaction> {
    public LoanTransactionBusinessEvent(LoanTransaction value) {
        super(value);
    }
}
