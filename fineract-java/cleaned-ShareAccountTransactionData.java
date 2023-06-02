
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
@SuppressWarnings("unused")
public class ShareAccountTransactionData implements Serializable {
    private final Long id;
    private final Long accountId;
    private final LocalDate purchasedDate;
    private final Long numberOfShares;
    private final BigDecimal purchasedPrice;
    private final EnumOptionData status;
    private final EnumOptionData type;
    private final BigDecimal amount;
    private final BigDecimal chargeAmount;
    private final BigDecimal amountPaid;
    public ShareAccountTransactionData(final Long id, final Long accountId, final LocalDate purchasedDate, final Long numberOfShares,
            final BigDecimal purchasedPrice, final EnumOptionData status, final EnumOptionData type, final BigDecimal amount,
            final BigDecimal chargeAmount, final BigDecimal amountPaid) {
        this.id = id;
        this.accountId = accountId;
        this.purchasedDate = purchasedDate;
        this.numberOfShares = numberOfShares;
        this.purchasedPrice = purchasedPrice;
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.chargeAmount = chargeAmount;
        this.amountPaid = amountPaid;
    }
    public LocalDate getPurchasedDate() {
        return this.purchasedDate;
    }
    public Long getNumberOfShares() {
        return this.numberOfShares;
    }
    public BigDecimal getPurchasedPrice() {
        return this.purchasedPrice;
    }
    public EnumOptionData getStatus() {
        return this.status;
    }
    public EnumOptionData getType() {
        return this.type;
    }
}
