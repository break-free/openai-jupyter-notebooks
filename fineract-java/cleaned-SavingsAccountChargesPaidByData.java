
package org.apache.fineract.portfolio.savings.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.savings.data.SavingsAccountChargeData;
public class SavingsAccountChargesPaidByData implements Serializable {
    private final Long chargeId;
    private final BigDecimal amount;
    private SavingsAccountChargeData savingsAccountChargeData;
    public SavingsAccountChargesPaidByData(final Long chargeId, final BigDecimal amount) {
        this.chargeId = chargeId;
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public Long getChargeId() {
        return this.chargeId;
    }
    public void setSavingsAccountChargeData(final SavingsAccountChargeData savingsAccountChargeData) {
        this.savingsAccountChargeData = savingsAccountChargeData;
    }
    public static SavingsAccountChargesPaidByData instance(final Long savingsAccountChargeId, final BigDecimal amount) {
        return new SavingsAccountChargesPaidByData(savingsAccountChargeId, amount);
    }
    public boolean isFeeCharge() {
        return (this.savingsAccountChargeData == null) ? false : this.savingsAccountChargeData.isFeeCharge();
    }
    public boolean isPenaltyCharge() {
        return (this.savingsAccountChargeData == null) ? false : this.savingsAccountChargeData.isPenaltyCharge();
    }
    public SavingsAccountChargeData getSavingsAccountCharge() {
        return this.savingsAccountChargeData;
    }
}
