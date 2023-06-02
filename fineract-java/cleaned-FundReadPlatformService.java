
package org.apache.fineract.portfolio.fund.service;
import java.util.Collection;
import org.apache.fineract.portfolio.fund.data.FundData;
public interface FundReadPlatformService {
    Collection<FundData> retrieveAllFunds();
    FundData retrieveFund(Long fundId);
}
