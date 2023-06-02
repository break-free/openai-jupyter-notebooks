
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.portfolio.savings.data.DepositAccountInterestRateChartData;
public interface DepositAccountInterestRateChartReadPlatformService {
    DepositAccountInterestRateChartData retrieveOne(Long interestChartId);
    DepositAccountInterestRateChartData retrieveOneWithSlabs(Long interestChartId);
    DepositAccountInterestRateChartData retrieveWithTemplate(DepositAccountInterestRateChartData depositAccountInterestRateChartData);
    DepositAccountInterestRateChartData retrieveOneWithSlabsOnAccountId(Long accountId);
    DepositAccountInterestRateChartData template();
}
