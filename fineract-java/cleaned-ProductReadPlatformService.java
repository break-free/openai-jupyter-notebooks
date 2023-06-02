
package org.apache.fineract.portfolio.products.service;
import java.util.Collection;
import java.util.Set;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.portfolio.products.data.ProductData;
public interface ProductReadPlatformService {
    Page<ProductData> retrieveAllProducts(Integer offSet, Integer limit);
    ProductData retrieveOne(Long productId, boolean includeTemplate);
    ProductData retrieveTemplate();
    Set<String> getResponseDataParams();
    Collection<ProductData> retrieveAllForLookup();
}
