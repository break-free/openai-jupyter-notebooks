
package org.apache.fineract.portfolio.loanproduct.productmix.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ProductMixNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ProductMixNotFoundException(final Long productId) {
        super("error.msg.no.product.mixes.exists", "No product mixes are defined with the productId `" + productId + "`.", productId);
    }
    public ProductMixNotFoundException(Long productId, EmptyResultDataAccessException e) {
        super("error.msg.no.product.mixes.exists", "No product mixes are defined with the productId `" + productId + "`.", productId, e);
    }
}
