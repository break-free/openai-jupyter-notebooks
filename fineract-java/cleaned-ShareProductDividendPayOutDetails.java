
package org.apache.fineract.portfolio.shareproducts.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccountDividendDetails;
@Entity
@Table(name = "m_share_product_dividend_pay_out")
public class ShareProductDividendPayOutDetails extends AbstractAuditableCustom {
    @Column(name = "product_id", nullable = true)
    private Long shareProductId;
    @Column(name = "amount", scale = 6, precision = 19)
    private BigDecimal amount;
    @Column(name = "dividend_period_start_date")
    private LocalDate dividendPeriodStartDate;
    @Column(name = "dividend_period_end_date")
    private LocalDate dividendPeriodEndDate;
    @Column(name = "status", nullable = false)
    private Integer status;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "productDividentPayOutDetails")
    private List<ShareAccountDividendDetails> accountDividendDetails = new ArrayList<>();
    protected ShareProductDividendPayOutDetails() {
    }
    public ShareProductDividendPayOutDetails(final Long shareProductId, final BigDecimal amount, final LocalDate dividendPeriodStartDate,
            final LocalDate dividendPeriodEndDate) {
        this.shareProductId = shareProductId;
        this.amount = amount;
        this.dividendPeriodStartDate = dividendPeriodStartDate;
        this.dividendPeriodEndDate = dividendPeriodEndDate;
        this.status = ShareProductDividendStatusType.INITIATED.getValue();
    }
    public LocalDate getDividendPeriodEndDateAsLocalDate() {
        return this.dividendPeriodEndDate;
    }
    public List<ShareAccountDividendDetails> getAccountDividendDetails() {
        return this.accountDividendDetails;
    }
    public void approveDividendPayout() {
        this.status = ShareProductDividendStatusType.APPROVED.getValue();
    }
    public ShareProductDividendStatusType getStatus() {
        return ShareProductDividendStatusType.fromInt(this.status);
    }
}
