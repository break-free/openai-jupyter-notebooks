
package org.apache.fineract.portfolio.businessevent.domain.savings.transaction;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
public class SavingsDepositBusinessEvent extends SavingsAccountTransactionBusinessEvent {
    public SavingsDepositBusinessEvent(SavingsAccountTransaction value) {
        super(value);
    }
}
