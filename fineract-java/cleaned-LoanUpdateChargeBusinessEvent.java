
package org.apache.fineract.portfolio.businessevent.domain.loan.charge;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanUpdateChargeBusinessEvent extends LoanChargeBusinessEvent {
    public LoanUpdateChargeBusinessEvent(LoanCharge value) {
        super(value);
    }
}
