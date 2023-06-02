
package org.apache.fineract.portfolio.floatingrates.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface FloatingRateWritePlatformService {
    CommandProcessingResult createFloatingRate(JsonCommand command);
    CommandProcessingResult updateFloatingRate(JsonCommand command);
}
