
package org.apache.fineract.portfolio.self.pockets.service;
import org.apache.fineract.infrastructure.accountnumberformat.domain.EntityAccountType;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.loanaccount.exception.LoanNotFoundException;
import org.apache.fineract.portfolio.loanaccount.service.LoanReadPlatformService;
import org.apache.fineract.portfolio.self.loanaccount.service.AppuserLoansMapperReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountEntityServiceForLoanImpl implements AccountEntityService {
    private final String key = EntityAccountType.LOAN.name();
    private final PlatformSecurityContext context;
    private final AppuserLoansMapperReadService appuserLoansMapperReadService;
    private final LoanReadPlatformService loanReadPlatformService;
    @Autowired
    public AccountEntityServiceForLoanImpl(final PlatformSecurityContext context,
            final AppuserLoansMapperReadService appuserLoansMapperReadService, final LoanReadPlatformService loanReadPlatformService) {
        this.context = context;
        this.appuserLoansMapperReadService = appuserLoansMapperReadService;
        this.loanReadPlatformService = loanReadPlatformService;
    }
    @Override
    public String getKey() {
        return key;
    }
    @Override
    public void validateSelfUserAccountMapping(Long accountId) {
        if (!this.appuserLoansMapperReadService.isLoanMappedToUser(accountId, this.context.authenticatedUser().getId())) {
            throw new LoanNotFoundException(accountId);
        }
    }
    @Override
    public String retrieveAccountNumberByAccountId(Long accountId) {
        return this.loanReadPlatformService.retrieveAccountNumberByAccountId(accountId);
    }
}
