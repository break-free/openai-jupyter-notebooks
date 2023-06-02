
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ClientIdentifierWritePlatformService {
    CommandProcessingResult addClientIdentifier(Long clientId, JsonCommand command);
    CommandProcessingResult updateClientIdentifier(Long clientId, Long clientIdentifierId, JsonCommand command);
    CommandProcessingResult deleteClientIdentifier(Long clientId, Long clientIdentifierId, Long commandId);
}
