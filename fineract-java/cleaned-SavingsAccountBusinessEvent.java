
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public abstract class SavingsAccountBusinessEvent extends AbstractBusinessEvent<SavingsAccount> {
    public SavingsAccountBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
