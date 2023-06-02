
package org.apache.fineract.infrastructure.creditbureau.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CreditBureauConfigurationWritePlatformService {
    CommandProcessingResult addCreditBureauConfiguration(Long creditBureauId, JsonCommand command);
    CommandProcessingResult updateCreditBureauConfiguration(Long configurationId, JsonCommand command);
}
