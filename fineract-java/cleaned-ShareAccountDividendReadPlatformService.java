
package org.apache.fineract.portfolio.shareaccounts.service;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountDividendData;
public interface ShareAccountDividendReadPlatformService {
    List<Map<String, Object>> retriveDividendDetailsForPostDividents();
    Page<ShareAccountDividendData> retriveAll(Long payoutDetailId, SearchParameters searchParameters);
}
