
package org.apache.fineract.portfolio.tax.data;
import java.math.BigDecimal;
import org.apache.fineract.organisation.monetary.domain.Money;
public class TaxDetailsData {
    private TaxComponentData taxComponent;
    private BigDecimal amount;
    protected TaxDetailsData() {}
    public TaxDetailsData(final TaxComponentData taxComponent, final BigDecimal amount) {
        this.taxComponent = taxComponent;
        this.amount = amount;
    }
    public TaxComponentData getTaxComponent() {
        return this.taxComponent;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public void updateAmount(Money amount) {
        this.amount = amount.getAmount();
    }
}
