
package org.apache.fineract.organisation.teller.data;
import java.io.Serializable;
import java.math.BigDecimal;
public final class CashierTransactionTypeTotalsData implements Serializable {
    private final Integer cashierTxnType;
    private final BigDecimal cashTotal;
    private CashierTransactionTypeTotalsData(final Integer cashierTxnType, final BigDecimal cashTotal) {
        this.cashierTxnType = cashierTxnType;
        this.cashTotal = cashTotal;
    }
    public static CashierTransactionTypeTotalsData instance(final Integer cashierTxnType, final BigDecimal cashTotal) {
        return new CashierTransactionTypeTotalsData(cashierTxnType, cashTotal);
    }
    public Integer getCashierTxnType() {
        return cashierTxnType;
    }
    public BigDecimal getCashTotal() {
        return cashTotal;
    }
}
