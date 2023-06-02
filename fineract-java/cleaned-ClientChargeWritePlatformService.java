
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.transaction.annotation.Transactional;
public interface ClientChargeWritePlatformService {
    @Transactional
    CommandProcessingResult addCharge(Long clientId, JsonCommand command);
    @Transactional
    CommandProcessingResult updateCharge(Long clientId, JsonCommand command);
    @Transactional
    CommandProcessingResult deleteCharge(Long clientId, Long clientChargeId);
    @Transactional
    CommandProcessingResult waiveCharge(Long clientId, Long clientChargeId);
    @Transactional
    CommandProcessingResult payCharge(Long clientId, Long clientChargeId, JsonCommand command);
    @Transactional
    CommandProcessingResult inactivateCharge(Long clientId, Long clientChargeId);
}
