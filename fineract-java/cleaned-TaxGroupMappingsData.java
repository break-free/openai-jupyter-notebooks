
package org.apache.fineract.portfolio.tax.data;
import java.io.Serializable;
import java.time.LocalDate;
public class TaxGroupMappingsData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final TaxComponentData taxComponent;
    @SuppressWarnings("unused")
    private final LocalDate startDate;
    @SuppressWarnings("unused")
    private final LocalDate endDate;
    public TaxGroupMappingsData(final Long id, final TaxComponentData taxComponent, final LocalDate startDate, final LocalDate endDate) {
        this.id = id;
        this.taxComponent = taxComponent;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public TaxComponentData getTaxComponent() {
        return this.taxComponent;
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
}
