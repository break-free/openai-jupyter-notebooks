
package org.apache.fineract.portfolio.shareproducts.service;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.shareproducts.data.ShareProductDividendPayOutData;
public interface ShareProductDividendReadPlatformService {
    Page<ShareProductDividendPayOutData> retriveAll(Long productId, Integer status, SearchParameters searchParameters);
}
