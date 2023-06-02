
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanAddChargeBusinessEvent extends LoanChargeBusinessEvent {
    public LoanAddChargeBusinessEvent(LoanCharge value) {
        super(value);
    }
}
