
package org.apache.fineract.organisation.provisioning.domain;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
@Entity
@Table(name = "m_loanproduct_provisioning_mapping", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "product_id" }, name = "product_id") })
public class LoanProductProvisionCriteria extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "criteria_id", referencedColumnName = "id", nullable = false)
    private ProvisioningCriteria criteria;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private LoanProduct loanProduct;
    protected LoanProductProvisionCriteria() {
    }
    public LoanProductProvisionCriteria(ProvisioningCriteria criteria, LoanProduct loanProduct) {
        this.criteria = criteria;
        this.loanProduct = loanProduct;
    }
    public LoanProduct getLoanProduct() {
        return this.loanProduct;
    }
}
