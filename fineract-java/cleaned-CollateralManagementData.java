
package org.apache.fineract.portfolio.collateralmanagement.data;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementDomain;
public final class CollateralManagementData {
    private String quality;
    private BigDecimal basePrice;
    private String unitType;
    private BigDecimal pctToBase;
    private String currency;
    private String name;
    private Long id;
    private CollateralManagementData(final String quality, final BigDecimal basePrice, final String unitType, final BigDecimal pctToBase,
            final String currency, final String name, final Long id) {
        this.basePrice = basePrice;
        this.pctToBase = pctToBase;
        this.quality = quality;
        this.unitType = unitType;
        this.currency = currency;
        this.name = name;
        this.id = id;
    }
    public static CollateralManagementData createNew(final CollateralManagementDomain collateralManagementDomain) {
        return new CollateralManagementData(collateralManagementDomain.getQuality(), collateralManagementDomain.getBasePrice(),
                collateralManagementDomain.getUnitType(), collateralManagementDomain.getPctToBase(),
                collateralManagementDomain.getCurrency().getCode(), collateralManagementDomain.getName(),
                collateralManagementDomain.getId());
    }
}
