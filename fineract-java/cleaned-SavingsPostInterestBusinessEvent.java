
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsPostInterestBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsPostInterestBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
