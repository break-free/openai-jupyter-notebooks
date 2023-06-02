
package org.apache.fineract.portfolio.self.pockets.service;
import org.apache.fineract.infrastructure.accountnumberformat.domain.EntityAccountType;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.savings.exception.SavingsAccountNotFoundException;
import org.apache.fineract.portfolio.savings.service.SavingsAccountReadPlatformService;
import org.apache.fineract.portfolio.self.savings.service.AppuserSavingsMapperReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountEntityServiceForSavingsImpl implements AccountEntityService {
    private final String key = EntityAccountType.SAVINGS.name();
    private final PlatformSecurityContext context;
    private final AppuserSavingsMapperReadService appuserSavingsMapperReadService;
    private final SavingsAccountReadPlatformService savingsAccountReadPlatformService;
    @Autowired
    public AccountEntityServiceForSavingsImpl(final PlatformSecurityContext context,
            final AppuserSavingsMapperReadService appuserSavingsMapperReadService,
            final SavingsAccountReadPlatformService savingsAccountReadPlatformService) {
        this.context = context;
        this.appuserSavingsMapperReadService = appuserSavingsMapperReadService;
        this.savingsAccountReadPlatformService = savingsAccountReadPlatformService;
    }
    @Override
    public String getKey() {
        return key;
    }
    @Override
    public void validateSelfUserAccountMapping(Long accountId) {
        if (!this.appuserSavingsMapperReadService.isSavingsMappedToUser(accountId, this.context.getAuthenticatedUserIfPresent().getId())) {
            throw new SavingsAccountNotFoundException(accountId);
        }
    }
    @Override
    public String retrieveAccountNumberByAccountId(Long accountId) {
        return this.savingsAccountReadPlatformService.retrieveAccountNumberByAccountId(accountId);
    }
}
