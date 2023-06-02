
package org.apache.fineract.portfolio.businessevent.domain.savings;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class SavingsApproveBusinessEvent extends SavingsAccountBusinessEvent {
    public SavingsApproveBusinessEvent(SavingsAccount value) {
        super(value);
    }
}
