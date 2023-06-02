
package org.apache.fineract.portfolio.loanaccount.domain;
import org.apache.fineract.organisation.monetary.domain.Money;
public class TransactionProccessingResult {
    private final Money overPaymentAmount;
    private final boolean overPayment;
    public TransactionProccessingResult(final Money overPaymentAmount, final boolean overPayment) {
        this.overPaymentAmount = overPaymentAmount;
        this.overPayment = overPayment;
    }
    public Money getOverPaymentAmount() {
        return this.overPaymentAmount;
    }
    public boolean isOverPayment() {
        return this.overPayment;
    }
}
