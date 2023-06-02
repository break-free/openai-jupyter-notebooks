
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanWaiveChargeBusinessEvent extends LoanChargeBusinessEvent {
    public LoanWaiveChargeBusinessEvent(LoanCharge value) {
        super(value);
    }
}
