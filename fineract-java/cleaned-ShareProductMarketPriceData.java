
package org.apache.fineract.portfolio.shareproducts.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public class ShareProductMarketPriceData {
    private final Long id;
    private final LocalDate fromDate;
    private final BigDecimal shareValue;
    public ShareProductMarketPriceData(final Long id, final LocalDate fromDate, final BigDecimal shareValue) {
        this.id = id;
        this.fromDate = fromDate;
        this.shareValue = shareValue;
    }
    public Long getId() {
        return this.id;
    }
    public LocalDate getStartDate() {
        return this.fromDate;
    }
    public BigDecimal getShareValue() {
        return this.shareValue;
    }
}
