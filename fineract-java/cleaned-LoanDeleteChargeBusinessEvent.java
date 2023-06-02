
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanDeleteChargeBusinessEvent extends LoanChargeBusinessEvent {
    public LoanDeleteChargeBusinessEvent(LoanCharge value) {
        super(value);
    }
}
