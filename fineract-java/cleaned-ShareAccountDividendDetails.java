
package org.apache.fineract.portfolio.shareaccounts.domain;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.shareproducts.domain.ShareProductDividendPayOutDetails;
@Entity
@Table(name = "m_share_account_dividend_details")
public class ShareAccountDividendDetails extends AbstractPersistableCustom {
    @Column(name = "account_id", nullable = false)
    private Long shareAccountId;
    @Column(name = "amount", scale = 6, precision = 19)
    private BigDecimal amount;
    @Column(name = "status")
    private Integer status;
    @Column(name = "savings_transaction_id")
    private Long savingsTransactionId;
    @ManyToOne
    @JoinColumn(name = "dividend_pay_out_id", nullable = false)
    private ShareProductDividendPayOutDetails productDividentPayOutDetails;
    protected ShareAccountDividendDetails() {
    }
    public ShareAccountDividendDetails(final Long shareAccountId, final BigDecimal amount,
            final ShareProductDividendPayOutDetails productDividentPayOutDetails) {
        this.shareAccountId = shareAccountId;
        this.amount = amount;
        this.productDividentPayOutDetails = productDividentPayOutDetails;
        this.status = ShareAccountDividendStatusType.INITIATED.getValue();
    }
    public void update(final Integer status, final Long savingsTransactionId) {
        this.status = status;
        this.savingsTransactionId = savingsTransactionId;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public ShareProductDividendPayOutDetails getProductDividentPayOutDetails() {
        return productDividentPayOutDetails;
    }
}
