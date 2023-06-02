
package org.apache.fineract.portfolio.rate.data;
import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class RateData implements Serializable {
    private Long id;
    private String name;
    private BigDecimal percentage;
    private EnumOptionData productApply;
    private boolean active;
    public static RateData instance(final Long id, final String name, final BigDecimal percentage, final EnumOptionData productApply,
            final boolean active) {
        return new RateData(id, name, percentage, productApply, active);
    }
    private RateData(final Long id, final String name, final BigDecimal percentage, final EnumOptionData productApply,
            final boolean active) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.productApply = productApply;
        this.active = active;
    }
}
