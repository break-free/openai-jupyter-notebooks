
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.charge.data.ChargeData;
@SuppressWarnings("unused")
public class ShareAccountChargeData implements Serializable {
    private final Long id;
    private final Long chargeId;
    private final Long accountId;
    private final String name;
    private final EnumOptionData chargeTimeType;
    private final EnumOptionData chargeCalculationType;
    private final BigDecimal percentage;
    private final BigDecimal amountPercentageAppliedTo;
    private final CurrencyData currency;
    private final BigDecimal amount;
    private final BigDecimal amountPaid;
    private final BigDecimal amountWaived;
    private final BigDecimal amountWrittenOff;
    private final BigDecimal amountOutstanding;
    private final BigDecimal amountOrPercentage;
    private final Boolean isActive;
    private final Collection<ChargeData> chargeOptions;
    public ShareAccountChargeData(Long chargeId, BigDecimal amount) {
        this.chargeId = chargeId;
        this.amount = amount;
        this.id = null;
        this.accountId = null;
        this.name = null;
        this.chargeTimeType = null;
        this.chargeCalculationType = null;
        this.percentage = null;
        this.amountPercentageAppliedTo = null;
        this.currency = null;
        this.amountPaid = null;
        this.amountWaived = null;
        this.amountWrittenOff = null;
        this.amountOutstanding = null;
        this.amountOrPercentage = null;
        this.isActive = null;
        this.chargeOptions = null;
    }
    public ShareAccountChargeData(final Long id, final Long chargeId, final Long accountId, final String name, final CurrencyData currency,
            final BigDecimal amount, final BigDecimal amountPaid, final BigDecimal amountWaived, final BigDecimal amountWrittenOff,
            final BigDecimal amountOutstanding, final EnumOptionData chargeTimeType, final EnumOptionData chargeCalculationType,
            final BigDecimal percentage, final BigDecimal amountPercentageAppliedTo, final Collection<ChargeData> chargeOptions,
            final Boolean isActive, final BigDecimal chargeamountorpercentage) {
        this.id = id;
        this.chargeId = chargeId;
        this.accountId = accountId;
        this.name = name;
        this.chargeTimeType = chargeTimeType;
        this.chargeCalculationType = chargeCalculationType;
        this.percentage = percentage;
        this.amountPercentageAppliedTo = amountPercentageAppliedTo;
        this.currency = currency;
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.amountWaived = amountWaived;
        this.amountWrittenOff = amountWrittenOff;
        this.amountOutstanding = amountOutstanding;
        this.amountOrPercentage = chargeamountorpercentage;
        this.chargeOptions = chargeOptions;
        this.isActive = isActive;
    }
    private BigDecimal getAmountOrPercentage() {
        return this.chargeCalculationType != null && this.chargeCalculationType.getId().intValue() > 1 ? this.percentage : this.amount;
    }
}
