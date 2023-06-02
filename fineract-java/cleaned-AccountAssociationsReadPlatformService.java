
package org.apache.fineract.portfolio.account.service;
import java.util.Collection;
import org.apache.fineract.portfolio.account.data.AccountAssociationsData;
import org.apache.fineract.portfolio.account.data.PortfolioAccountData;
public interface AccountAssociationsReadPlatformService {
    PortfolioAccountData retriveLoanLinkedAssociation(Long loanId);
    boolean isLinkedWithAnyActiveAccount(Long savingsId);
    PortfolioAccountData retriveSavingsLinkedAssociation(Long savingsId);
    Collection<AccountAssociationsData> retriveLoanAssociations(Long loanId, Integer associationType);
    PortfolioAccountData retriveSavingsAccount(Long savingsId);
}
