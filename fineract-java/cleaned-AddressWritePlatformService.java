
package org.apache.fineract.portfolio.address.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.domain.Client;
public interface AddressWritePlatformService {
    CommandProcessingResult addClientAddress(Long clientId, Long addressTypeId, JsonCommand command);
    CommandProcessingResult addNewClientAddress(Client client, JsonCommand command);
    CommandProcessingResult updateClientAddress(Long clientId, JsonCommand command);
}
