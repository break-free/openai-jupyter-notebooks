
package org.apache.fineract.portfolio.shareaccounts.service;
import java.util.Collection;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountChargeData;
public interface ShareAccountChargeReadPlatformService {
    Collection<ShareAccountChargeData> retrieveAccountCharges(Long accountId, String status);
}
