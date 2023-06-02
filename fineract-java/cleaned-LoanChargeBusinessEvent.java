
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public abstract class LoanChargeBusinessEvent extends AbstractBusinessEvent<LoanCharge> {
    public LoanChargeBusinessEvent(LoanCharge value) {
        super(value);
    }
}
