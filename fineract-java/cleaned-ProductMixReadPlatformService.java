
package org.apache.fineract.portfolio.loanproduct.productmix.service;
import java.util.Collection;
import org.apache.fineract.portfolio.loanproduct.productmix.data.ProductMixData;
public interface ProductMixReadPlatformService {
    ProductMixData retrieveLoanProductMixDetails(Long productId);
    Collection<ProductMixData> retrieveAllProductMixes();
}
