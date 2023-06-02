
package org.apache.fineract.portfolio.businessevent.domain.deposit;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.savings.domain.RecurringDepositAccount;
public abstract class RecurringDepositAccountBusinessEvent extends AbstractBusinessEvent<RecurringDepositAccount> {
    public RecurringDepositAccountBusinessEvent(RecurringDepositAccount value) {
        super(value);
    }
}
