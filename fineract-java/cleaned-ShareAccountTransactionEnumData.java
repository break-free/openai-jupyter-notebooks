
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
public class ShareAccountTransactionEnumData implements Serializable {
    private final Long id;
    private final String code;
    private final String value;
    private final boolean isApplied;
    private final boolean isApproved;
    private final boolean isRejected;
    private final boolean isPurchased;
    private final boolean isRedeemed;
    private final boolean isChargePayment;
    public ShareAccountTransactionEnumData(final Long id, final String code, final String value) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.isApplied = Long.valueOf(100).equals(this.id);
        this.isApproved = Long.valueOf(300).equals(this.id);
        this.isRejected = Long.valueOf(400).equals(this.id);
        this.isPurchased = Long.valueOf(500).equals(this.id);
        this.isRedeemed = Long.valueOf(600).equals(this.id);
        this.isChargePayment = Long.valueOf(700).equals(this.id);
    }
    public Long getId() {
        return this.id;
    }
    public String getCode() {
        return this.code;
    }
    public String getValue() {
        return this.value;
    }
    public boolean isApplied() {
        return this.isApplied;
    }
    public boolean isApproved() {
        return this.isApproved;
    }
    public boolean isRejected() {
        return this.isRejected;
    }
    public boolean isPurchased() {
        return this.isPurchased;
    }
    public boolean isRedeemed() {
        return this.isRedeemed;
    }
    public boolean isChargePayment() {
        return this.isChargePayment;
    }
}
