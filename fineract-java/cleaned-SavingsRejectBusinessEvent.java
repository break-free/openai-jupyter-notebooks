
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsRejectBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsRejectBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
