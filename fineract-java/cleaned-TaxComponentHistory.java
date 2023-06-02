
package org.apache.fineract.portfolio.tax.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
@Entity
@Table(name = "m_tax_component_history")
public class TaxComponentHistory extends AbstractAuditableCustom {
    @Column(name = "percentage", scale = 6, precision = 19, nullable = false)
    private BigDecimal percentage;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    protected TaxComponentHistory() {
    }
    private TaxComponentHistory(final BigDecimal percentage, final LocalDate startDate, final LocalDate endDate) {
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public static TaxComponentHistory createTaxComponentHistory(final BigDecimal percentage, final LocalDate startDate,
            final LocalDate endDate) {
        return new TaxComponentHistory(percentage, startDate, endDate);
    }
    public LocalDate startDate() {
        return this.startDate;
    }
    public LocalDate endDate() {
        return this.endDate;
    }
    public boolean occursOnDayFromAndUpToAndIncluding(final LocalDate target) {
        if (this.endDate == null) {
            return target != null && target.isAfter(startDate());
        }
        return target != null && target.isAfter(startDate()) && !target.isAfter(endDate());
    }
    public BigDecimal getPercentage() {
        return this.percentage;
    }
}
