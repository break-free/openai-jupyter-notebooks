
package org.apache.fineract.portfolio.interestratechart.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface InterestRateChartSlabWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long chartSlabId, Long interestChartId, JsonCommand command);
    CommandProcessingResult deleteChartSlab(Long chartSlabId, Long interestRateChartId);
}
