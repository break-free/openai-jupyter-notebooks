
package org.apache.fineract.portfolio.shareproducts.domain;
import org.apache.fineract.portfolio.products.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ShareProductRepositoryWrapper {
    private final ShareProductRepository shareProductRepository;
    @Autowired
    public ShareProductRepositoryWrapper(final ShareProductRepository shareProductRepository) {
        this.shareProductRepository = shareProductRepository;
    }
    public ShareProduct findOneWithNotFoundDetection(final Long productId) {
        return this.shareProductRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId, "share"));
    }
    public void save(ShareProduct product) {
        this.shareProductRepository.save(product);
    }
    public void saveAndFlush(ShareProduct product) {
        this.shareProductRepository.saveAndFlush(product);
    }
}
