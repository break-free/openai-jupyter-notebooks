
package org.apache.fineract.portfolio.businessevent.domain.deposit;
import org.apache.fineract.portfolio.savings.domain.RecurringDepositAccount;
public class RecurringDepositAccountCreateBusinessEvent extends RecurringDepositAccountBusinessEvent {
    public RecurringDepositAccountCreateBusinessEvent(RecurringDepositAccount value) {
        super(value);
    }
}
