
package org.apache.fineract.portfolio.loanproduct.productmix.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ProductMixWritePlatformService {
    CommandProcessingResult createProductMix(Long productId, JsonCommand command);
    CommandProcessingResult updateProductMix(Long productId, JsonCommand command);
    CommandProcessingResult deleteProductMix(Long productId);
}
