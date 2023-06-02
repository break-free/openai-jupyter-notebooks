
package org.apache.fineract.infrastructure.configuration.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ExternalServiceWritePlatformService {
    CommandProcessingResult updateExternalServicesProperties(String externalServiceName, JsonCommand command);
}
