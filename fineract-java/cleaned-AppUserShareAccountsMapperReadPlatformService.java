
package org.apache.fineract.portfolio.self.shareaccounts.service;
public interface AppUserShareAccountsMapperReadPlatformService {
    Boolean isShareAccountsMappedToUser(Long accountId, Long appUserId);
}
