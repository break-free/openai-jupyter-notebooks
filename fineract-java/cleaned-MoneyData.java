
package org.apache.fineract.organisation.monetary.data;
import java.math.BigDecimal;
public class MoneyData {
    private final String code;
    private final BigDecimal amount;
    private final int decimalPlaces;
    public MoneyData(final String code, final BigDecimal amount, final int decimalPlaces) {
        this.code = code;
        this.amount = amount;
        this.decimalPlaces = decimalPlaces;
    }
    public String getCode() {
        return this.code;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public int getDecimalPlaces() {
        return this.decimalPlaces;
    }
}
