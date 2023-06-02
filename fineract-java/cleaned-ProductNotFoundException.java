
package org.apache.fineract.portfolio.products.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ProductNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ProductNotFoundException(final Long id, String type) {
        super("error.msg.product.id.invalid", type + " product with identifier " + id + " does not exist", id);
    }
    public ProductNotFoundException(Long id, String type, EmptyResultDataAccessException e) {
        super("error.msg.product.id.invalid", type + " product with identifier " + id + " does not exist", id, e);
    }
}
