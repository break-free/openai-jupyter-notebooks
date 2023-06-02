
package org.apache.fineract.portfolio.businessevent.domain.savings.transaction;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
public abstract class SavingsAccountTransactionBusinessEvent extends AbstractBusinessEvent<SavingsAccountTransaction> {
    public SavingsAccountTransactionBusinessEvent(SavingsAccountTransaction value) {
        super(value);
    }
}
