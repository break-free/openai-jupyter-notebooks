
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanWaiveChargeUndoBusinessEvent extends LoanChargeBusinessEvent {
    public LoanWaiveChargeUndoBusinessEvent(LoanCharge value) {
        super(value);
    }
}
