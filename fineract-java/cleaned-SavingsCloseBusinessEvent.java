
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsCloseBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsCloseBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
