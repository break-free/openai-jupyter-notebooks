
package org.apache.fineract.portfolio.businessevent.domain.deposit;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.savings.domain.FixedDepositAccount;
public abstract class FixedDepositAccountBusinessEvent extends AbstractBusinessEvent<FixedDepositAccount> {
    public FixedDepositAccountBusinessEvent(FixedDepositAccount value) {
        super(value);
    }
}
