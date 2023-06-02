
package org.apache.fineract.portfolio.account.service;
import java.util.Collection;
import org.apache.fineract.portfolio.account.data.PortfolioAccountDTO;
import org.apache.fineract.portfolio.account.data.PortfolioAccountData;
public interface PortfolioAccountReadPlatformService {
    PortfolioAccountData retrieveOne(Long accountId, Integer accountTypeId);
    PortfolioAccountData retrieveOne(Long accountId, Integer accountTypeId, String currencyCode);
    Collection<PortfolioAccountData> retrieveAllForLookup(PortfolioAccountDTO portfolioAccountDTO);
    PortfolioAccountData retrieveOneByPaidInAdvance(Long accountId, Integer accountTypeId);
}
