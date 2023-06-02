
package org.apache.fineract.portfolio.shareproducts.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_share_product_market_price")
public class ShareProductMarketPrice extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ShareProduct product;
    @Column(name = "from_date")
    private LocalDate fromDate;
    @Column(name = "share_value", nullable = false)
    private BigDecimal shareValue;
    public ShareProductMarketPrice() {
    }
    public ShareProductMarketPrice(final LocalDate fromDate, final BigDecimal shareValue) {
        this.fromDate = fromDate;
        this.shareValue = shareValue;
    }
    public void setShareProduct(final ShareProduct product) {
        this.product = product;
    }
    public LocalDate getStartDate() {
        return this.fromDate;
    }
    public BigDecimal getPrice() {
        return this.shareValue;
    }
    public void setStartDate(LocalDate date) {
        this.fromDate = date;
    }
    public void setShareValue(BigDecimal value) {
        this.shareValue = value;
    }
}
