
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.tax.domain.TaxComponent;
@Entity
@Table(name = "m_savings_account_transaction_tax_details")
public class SavingsAccountTransactionTaxDetails extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "savings_transaction_id", nullable = false)
    private SavingsAccountTransaction savingsAccountTransaction;
    @ManyToOne
    @JoinColumn(name = "tax_component_id", nullable = false)
    private TaxComponent taxComponent;
    @Column(name = "amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal amount;
    protected SavingsAccountTransactionTaxDetails() {}
    public SavingsAccountTransactionTaxDetails(final SavingsAccountTransaction savingsAccountTransaction, final TaxComponent taxComponent,
            final BigDecimal amount) {
        this.savingsAccountTransaction = savingsAccountTransaction;
        this.taxComponent = taxComponent;
        this.amount = amount;
    }
    public TaxComponent getTaxComponent() {
        return this.taxComponent;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public void updateAmount(Money amount) {
        this.amount = amount.getAmount();
    }
    public SavingsAccountTransaction getSavingsAccountTransaction() {
        return savingsAccountTransaction;
    }
    public void setSavingsAccountTransaction(SavingsAccountTransaction savingsAccountTransaction) {
        this.savingsAccountTransaction = savingsAccountTransaction;
    }
}
