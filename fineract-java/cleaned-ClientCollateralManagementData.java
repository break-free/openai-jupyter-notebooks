
package org.apache.fineract.portfolio.collateralmanagement.data;
import java.math.BigDecimal;
import java.util.List;
public final class ClientCollateralManagementData {
    private final String name;
    private final BigDecimal quantity;
    private final BigDecimal total;
    private final BigDecimal totalCollateral;
    private final Long clientId;
    private final Long id;
    private final List<LoanTransactionData> loanTransactionData;
    private ClientCollateralManagementData(final String name, final BigDecimal quantity, final BigDecimal total,
            final BigDecimal totalCollateral, final Long clientId, final List<LoanTransactionData> loanTransactionData, final Long id) {
        this.clientId = clientId;
        this.totalCollateral = totalCollateral;
        this.total = total;
        this.name = name;
        this.quantity = quantity;
        this.loanTransactionData = loanTransactionData;
        this.id = id;
    }
    public static ClientCollateralManagementData instance(final String name, final BigDecimal quantity, final BigDecimal total,
            final BigDecimal totalCollateral, final Long clientId, final List<LoanTransactionData> loanTransactionData, final Long id) {
        return new ClientCollateralManagementData(name, quantity, total, totalCollateral, clientId, loanTransactionData, id);
    }
    public String getName() {
        return this.name;
    }
    public Long getClientId() {
        return this.clientId;
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
    public Long getId() {
        return this.id;
    }
    public List<LoanTransactionData> getLoanTransactionData() {
        return this.loanTransactionData;
    }
}
