
package org.apache.fineract.portfolio.collateralmanagement.data;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.loanaccount.data.LoanCollateralManagementData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCollateralManagement;
public final class LoanCollateralResponseData {
    private Long collateralId;
    private BigDecimal quantity;
    private BigDecimal total;
    private BigDecimal totalCollateral;
    private Long clientCollateralId;
    private LoanCollateralResponseData(final Long collateralId, final BigDecimal quantity, final BigDecimal total,
            final BigDecimal totalCollateral, final Long clientCollateralId) {
        this.collateralId = collateralId;
        this.quantity = quantity;
        this.total = total;
        this.totalCollateral = totalCollateral;
        this.clientCollateralId = clientCollateralId;
    }
    public static LoanCollateralResponseData instanceOf(final LoanCollateralManagement loanCollateralManagement, final BigDecimal total,
            final BigDecimal totalCollateral) {
        return new LoanCollateralResponseData(loanCollateralManagement.getId(), loanCollateralManagement.getQuantity(), total,
                totalCollateral, loanCollateralManagement.getClientCollateralManagement().getId());
    }
    public LoanCollateralManagementData toCommand() {
        return new LoanCollateralManagementData(this.clientCollateralId, this.quantity, this.total, this.totalCollateral,
                this.collateralId);
    }
}
