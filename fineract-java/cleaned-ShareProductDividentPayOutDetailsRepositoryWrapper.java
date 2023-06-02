
package org.apache.fineract.portfolio.shareproducts.domain;
import org.apache.fineract.portfolio.shareproducts.exception.DividendNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ShareProductDividentPayOutDetailsRepositoryWrapper {
    private final ShareProductDividentPayOutDetailsRepository shareProductDividentPayOutDetailsRepository;
    @Autowired
    public ShareProductDividentPayOutDetailsRepositoryWrapper(
            final ShareProductDividentPayOutDetailsRepository shareProductDividentPayOutDetailsRepository) {
        this.shareProductDividentPayOutDetailsRepository = shareProductDividentPayOutDetailsRepository;
    }
    public ShareProductDividendPayOutDetails findOneWithNotFoundDetection(final Long dividendId) {
        return this.shareProductDividentPayOutDetailsRepository.findById(dividendId)
                .orElseThrow(() -> new DividendNotFoundException(dividendId, "share"));
    }
    public void save(final ShareProductDividendPayOutDetails shareProductDividendPayOutDetails) {
        this.shareProductDividentPayOutDetailsRepository.save(shareProductDividendPayOutDetails);
    }
    public void delete(final ShareProductDividendPayOutDetails shareProductDividendPayOutDetails) {
        this.shareProductDividentPayOutDetailsRepository.delete(shareProductDividendPayOutDetails);
    }
}
