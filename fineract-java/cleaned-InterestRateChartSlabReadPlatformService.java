
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.Collection;
import org.apache.fineract.portfolio.interestratechart.data.InterestRateChartSlabData;
public interface InterestRateChartSlabReadPlatformService {
    Collection<InterestRateChartSlabData> retrieveAll(Long chartId);
    InterestRateChartSlabData retrieveOne(Long chartId, Long chartSlabId);
    InterestRateChartSlabData retrieveWithTemplate(InterestRateChartSlabData chartSlab);
    InterestRateChartSlabData retrieveTemplate();
}
