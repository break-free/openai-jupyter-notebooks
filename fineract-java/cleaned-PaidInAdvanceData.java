
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
public class PaidInAdvanceData {
    private final BigDecimal paidInAdvance;
    public PaidInAdvanceData(final BigDecimal paidInAdvance) {
        this.paidInAdvance = paidInAdvance;
    }
    public BigDecimal getPaidInAdvance() {
        return paidInAdvance;
    }
}
