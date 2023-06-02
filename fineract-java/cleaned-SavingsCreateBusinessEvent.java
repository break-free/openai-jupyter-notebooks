
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsCreateBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsCreateBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
