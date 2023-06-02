
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ClientWritePlatformService {
    CommandProcessingResult createClient(JsonCommand command);
    CommandProcessingResult updateClient(Long clientId, JsonCommand command);
    CommandProcessingResult activateClient(Long clientId, JsonCommand command);
    CommandProcessingResult deleteClient(Long clientId);
    CommandProcessingResult unassignClientStaff(Long clientId, JsonCommand command);
    CommandProcessingResult closeClient(Long clientId, JsonCommand command);
    CommandProcessingResult assignClientStaff(Long clientId, JsonCommand command);
    CommandProcessingResult updateDefaultSavingsAccount(Long clientId, JsonCommand command);
    CommandProcessingResult rejectClient(Long entityId, JsonCommand command);
    CommandProcessingResult withdrawClient(Long entityId, JsonCommand command);
    CommandProcessingResult reActivateClient(Long entityId, JsonCommand command);
    CommandProcessingResult undoRejection(Long entityId, JsonCommand command);
    CommandProcessingResult undoWithdrawal(Long entityId, JsonCommand command);
}
