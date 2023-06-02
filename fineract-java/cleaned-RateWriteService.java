
package org.apache.fineract.portfolio.rate.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface RateWriteService {
    CommandProcessingResult createRate(JsonCommand command);
    CommandProcessingResult updateRate(Long rateId, JsonCommand command);
}
