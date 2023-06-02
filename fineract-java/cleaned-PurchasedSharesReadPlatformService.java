
package org.apache.fineract.portfolio.shareaccounts.service;
import java.util.Collection;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountTransactionData;
public interface PurchasedSharesReadPlatformService {
    Collection<ShareAccountTransactionData> retrievePurchasedShares(Long accountId);
}
