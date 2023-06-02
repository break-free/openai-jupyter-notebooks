
package org.apache.fineract.portfolio.collateralmanagement.data;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.collateralmanagement.domain.ClientCollateralManagement;
public final class LoanCollateralTemplateData {
    private Long collateralId;
    private BigDecimal basePrice;
    private BigDecimal pctToBase;
    private BigDecimal quantity;
    private String name;
    private LoanCollateralTemplateData(final Long collateralId, final BigDecimal basePrice, final BigDecimal pctToBase,
            final BigDecimal quantity, final String name) {
        this.quantity = quantity;
        this.collateralId = collateralId;
        this.basePrice = basePrice;
        this.pctToBase = pctToBase;
        this.name = name;
    }
    public static LoanCollateralTemplateData instanceOf(final ClientCollateralManagement clientCollateralManagement) {
        return new LoanCollateralTemplateData(clientCollateralManagement.getId(),
                clientCollateralManagement.getCollaterals().getBasePrice(), clientCollateralManagement.getCollaterals().getPctToBase(),
                clientCollateralManagement.getQuantity(), clientCollateralManagement.getCollaterals().getName());
    }
}
