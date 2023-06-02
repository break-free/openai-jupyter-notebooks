
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
public class LoanCollateralManagementData {
    private final Long clientCollateralId;
    private final BigDecimal quantity;
    private final BigDecimal total;
    private final BigDecimal totalCollateral;
    private final Long id;
    public LoanCollateralManagementData(final Long clientCollateralIdId, final BigDecimal quantity, final BigDecimal total,
            final BigDecimal totalCollateral, final Long id) {
        this.clientCollateralId = clientCollateralIdId;
        this.quantity = quantity;
        this.totalCollateral = totalCollateral;
        this.total = total;
        this.id = id;
    }
    public Long getClientCollateralId() {
        return this.clientCollateralId;
    }
    public BigDecimal getQuantity() {
        return this.quantity;
    }
    public BigDecimal getTotal() {
        return this.total;
    }
    public BigDecimal getTotalCollateral() {
        return this.totalCollateral;
    }
}
