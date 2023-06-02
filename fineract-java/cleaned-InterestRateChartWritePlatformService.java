
package org.apache.fineract.portfolio.interestratechart.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface InterestRateChartWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long interestChartId, JsonCommand command);
    CommandProcessingResult deleteChart(Long interestChartId);
}
