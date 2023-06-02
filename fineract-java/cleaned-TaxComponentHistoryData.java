
package org.apache.fineract.portfolio.tax.data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
public class TaxComponentHistoryData implements Serializable {
    @SuppressWarnings("unused")
    private final BigDecimal percentage;
    @SuppressWarnings("unused")
    private final LocalDate startDate;
    @SuppressWarnings("unused")
    private final LocalDate endDate;
    public TaxComponentHistoryData(final BigDecimal percentage, final LocalDate startDate, final LocalDate endDate) {
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public boolean occursOnDayFromAndUpToAndIncluding(final LocalDate target) {
        if (this.endDate == null) {
            return target != null && target.isAfter(startDate());
        }
        return target != null && target.isAfter(startDate()) && !target.isAfter(endDate());
    }
    public LocalDate startDate() {
        LocalDate startDate = null;
        if (this.startDate != null) {
            startDate = this.startDate;
        }
        return startDate;
    }
    public LocalDate endDate() {
        LocalDate endDate = null;
        if (this.endDate != null) {
            endDate = this.endDate;
        }
        return endDate;
    }
    public BigDecimal getPercentage() {
        return this.percentage;
    }
}
