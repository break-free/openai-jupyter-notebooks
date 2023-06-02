
package org.apache.fineract.portfolio.collateralmanagement.domain;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.collateralmanagement.api.CollateralManagementJsonInputParams;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCollateralManagement;
@Entity
@Table(name = "m_client_collateral_management")
public class ClientCollateralManagement extends AbstractPersistableCustom {
    @Column(name = "quantity", nullable = false, scale = 5, precision = 20)
    private BigDecimal quantity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(optional = false)
    @JoinColumn(name = "collateral_id", nullable = false)
    private CollateralManagementDomain collateral;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientCollateralManagement", fetch = FetchType.EAGER)
    private Set<LoanCollateralManagement> loanCollateralManagementSet = new HashSet<>();
    public ClientCollateralManagement() {
    }
    public ClientCollateralManagement(final BigDecimal quantity, final Client client) {
        this.client = client;
        this.quantity = quantity;
    }
    public ClientCollateralManagement(final BigDecimal quantity) {
        this.quantity = quantity;
    }
    private ClientCollateralManagement(final BigDecimal quantity, final Client client,
            final CollateralManagementDomain collateralManagementData) {
        this.quantity = quantity;
        this.client = client;
        this.collateral = collateralManagementData;
    }
    public ClientCollateralManagement createNew(JsonCommand jsonCommand) {
        BigDecimal quantity = jsonCommand.bigDecimalValueOfParameterNamed("quantity");
        return new ClientCollateralManagement(quantity);
    }
    public static ClientCollateralManagement createNew(final BigDecimal quantity, final Client client,
            final CollateralManagementDomain collateral) {
        return new ClientCollateralManagement(quantity, client, collateral);
    }
    public Map<String, Object> update(JsonCommand command) {
        final Map<String, Object> changes = new LinkedHashMap<>(3);
        final String quantity = CollateralManagementJsonInputParams.QUANTITY.getValue();
        if (command.isChangeInBigDecimalParameterNamed(quantity, this.quantity)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(quantity);
            this.quantity = newValue;
            changes.put(quantity, this.quantity);
        }
        return changes;
    }
    public BigDecimal getTotal() {
        if (BigDecimal.ZERO.compareTo(this.quantity) == 0) {
            return BigDecimal.ZERO;
        }
        return this.quantity.multiply(getCollaterals().getBasePrice());
    }
    public BigDecimal getTotalCollateral(BigDecimal total) {
        if (BigDecimal.ZERO.compareTo(total) == 0) {
            return BigDecimal.ZERO;
        }
        return total.multiply(getCollaterals().getPctToBase().divide(BigDecimal.valueOf(100)));
    }
    public void updateQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public void updateQuantityAfterLoanClosed(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
    }
    public BigDecimal getQuantity() {
        return this.quantity;
    }
    public Client getClient() {
        return this.client;
    }
    public CollateralManagementDomain getCollaterals() {
        return this.collateral;
    }
    public Set<LoanCollateralManagement> getLoanCollateralManagementSet() {
        return this.loanCollateralManagementSet;
    }
}
