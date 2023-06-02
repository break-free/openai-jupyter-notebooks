
package org.apache.fineract.portfolio.businessevent.domain.savings.transaction;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
public class SavingsWithdrawalBusinessEvent extends SavingsAccountTransactionBusinessEvent {
    public SavingsWithdrawalBusinessEvent(SavingsAccountTransaction value) {
        super(value);
    }
}
