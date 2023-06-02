
package org.apache.fineract.portfolio.accounts.service;
import java.util.Set;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.portfolio.accounts.data.AccountData;
public interface AccountReadPlatformService {
    AccountData retrieveTemplate(Long clientId, Long productId);
    AccountData retrieveOne(Long id, boolean includeTemplate);
    Page<AccountData> retrieveAll(Integer offSet, Integer limit);
    Set<String> getResponseDataParams();
}
