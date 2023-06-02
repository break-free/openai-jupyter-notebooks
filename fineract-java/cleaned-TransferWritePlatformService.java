
package org.apache.fineract.portfolio.transfer.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface TransferWritePlatformService {
    CommandProcessingResult transferClientsBetweenGroups(Long sourceGroupId, JsonCommand jsonCommand);
    CommandProcessingResult proposeClientTransfer(Long clientId, JsonCommand jsonCommand);
    CommandProcessingResult withdrawClientTransfer(Long clientId, JsonCommand jsonCommand);
    CommandProcessingResult acceptClientTransfer(Long clientId, JsonCommand jsonCommand);
    CommandProcessingResult rejectClientTransfer(Long clientId, JsonCommand jsonCommand);
    CommandProcessingResult proposeAndAcceptClientTransfer(Long clientId, JsonCommand jsonCommand);
}
