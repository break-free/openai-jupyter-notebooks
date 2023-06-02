
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsActivateBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsActivateBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
