
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.portfolio.interestratechart.data.InterestRateChartData;
public interface InterestRateChartReadPlatformService {
    InterestRateChartData retrieveOne(Long interestChartId);
    Collection<InterestRateChartData> retrieveAllWithSlabs(Long savingsProductId);
    Collection<InterestRateChartData> retrieveAllWithSlabsWithTemplate(Long savingsProductId);
    InterestRateChartData retrieveOneWithSlabs(Long interestChartId);
    InterestRateChartData retrieveWithTemplate(InterestRateChartData interestRateChartData);
    InterestRateChartData retrieveOneWithSlabsOnProductId(Long productId);
    InterestRateChartData template();
    InterestRateChartData retrieveActiveChartWithTemplate(Long productId);
}
