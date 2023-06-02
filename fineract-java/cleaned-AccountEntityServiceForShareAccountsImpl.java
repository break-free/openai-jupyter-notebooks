
package org.apache.fineract.portfolio.self.pockets.service;
import org.apache.fineract.infrastructure.accountnumberformat.domain.EntityAccountType;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.accounts.exceptions.ShareAccountNotFoundException;
import org.apache.fineract.portfolio.self.shareaccounts.service.AppUserShareAccountsMapperReadPlatformService;
import org.apache.fineract.portfolio.shareaccounts.service.ShareAccountReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountEntityServiceForShareAccountsImpl implements AccountEntityService {
    private final String key = EntityAccountType.SHARES.name();
    private final PlatformSecurityContext context;
    private final AppUserShareAccountsMapperReadPlatformService appUserShareAccountsMapperReadPlatformService;
    private final ShareAccountReadPlatformService shareAccountReadPlatformService;
    @Autowired
    public AccountEntityServiceForShareAccountsImpl(final PlatformSecurityContext context,
            final AppUserShareAccountsMapperReadPlatformService appUserShareAccountsMapperReadPlatformService,
            final ShareAccountReadPlatformService shareAccountReadPlatformService) {
        this.context = context;
        this.appUserShareAccountsMapperReadPlatformService = appUserShareAccountsMapperReadPlatformService;
        this.shareAccountReadPlatformService = shareAccountReadPlatformService;
    }
    @Override
    public String getKey() {
        return key;
    }
    @Override
    public void validateSelfUserAccountMapping(Long accountId) {
        if (!this.appUserShareAccountsMapperReadPlatformService.isShareAccountsMappedToUser(accountId,
                this.context.getAuthenticatedUserIfPresent().getId())) {
            throw new ShareAccountNotFoundException(accountId);
        }
    }
    @Override
    public String retrieveAccountNumberByAccountId(Long accountId) {
        return this.shareAccountReadPlatformService.retrieveAccountNumberByAccountId(accountId);
    }
}
