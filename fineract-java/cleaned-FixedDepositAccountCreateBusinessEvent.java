
package org.apache.fineract.portfolio.businessevent.domain.deposit;
import org.apache.fineract.portfolio.savings.domain.FixedDepositAccount;
public class FixedDepositAccountCreateBusinessEvent extends FixedDepositAccountBusinessEvent {
    public FixedDepositAccountCreateBusinessEvent(FixedDepositAccount value) {
        super(value);
    }
}
