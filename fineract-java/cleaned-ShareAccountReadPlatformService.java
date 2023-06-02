
package org.apache.fineract.portfolio.shareaccounts.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import org.apache.fineract.portfolio.accounts.service.AccountReadPlatformService;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountData;
public interface ShareAccountReadPlatformService extends AccountReadPlatformService {
    @Override
    ShareAccountData retrieveTemplate(Long clientId, Long productId);
    @Override
    ShareAccountData retrieveOne(Long id, boolean includeTemplate);
    @Override
    Set<String> getResponseDataParams();
    Collection<ShareAccountData> retrieveAllShareAccountDataForDividends(Long productId, boolean fetchInActiveAccounts,
            LocalDate startDate);
    String retrieveAccountNumberByAccountId(Long accountId);
}
