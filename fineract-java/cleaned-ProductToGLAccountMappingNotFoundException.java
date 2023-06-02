
package org.apache.fineract.accounting.producttoaccountmapping.exception;
import org.apache.fineract.accounting.producttoaccountmapping.domain.PortfolioProductType;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProductToGLAccountMappingNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ProductToGLAccountMappingNotFoundException(final PortfolioProductType type, final Long productId, final String accountType) {
        super("error.msg.productToAccountMapping.not.found", "Mapping for product of type " + type.toString() + " with Id " + productId
                + " does not exist for an account of type " + accountType, type.toString(), productId, accountType);
    }
}
