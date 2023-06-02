
package org.apache.fineract.portfolio.charge.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ChargeWritePlatformService {
    CommandProcessingResult createCharge(JsonCommand command);
    CommandProcessingResult updateCharge(Long chargeId, JsonCommand command);
    CommandProcessingResult deleteCharge(Long chargeId);
}
