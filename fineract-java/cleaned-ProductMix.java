
package org.apache.fineract.portfolio.loanproduct.productmix.domain;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
@Entity
@Table(name = "m_product_mix")
public class ProductMix extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private LoanProduct product;
    @ManyToOne
    @JoinColumn(name = "restricted_product_id", nullable = false)
    private LoanProduct restrictedProduct;
    public ProductMix() {
    }
    private ProductMix(final LoanProduct product, final LoanProduct restrictedProduct) {
        this.product = product;
        this.restrictedProduct = restrictedProduct;
    }
    public static ProductMix createNew(final LoanProduct product, final LoanProduct restrictedProduct) {
        return new ProductMix(product, restrictedProduct);
    }
    public Long getRestrictedProductId() {
        return this.restrictedProduct.getId();
    }
    public Long getProductId() {
        return this.product.getId();
    }
}
