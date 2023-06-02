
package org.apache.fineract.portfolio.shareaccounts.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ShareAccountWritePlatformService {
    CommandProcessingResult createShareAccount(JsonCommand jsonCommand);
    CommandProcessingResult updateShareAccount(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult approveShareAccount(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult activateShareAccount(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult rejectShareAccount(Long entityId, JsonCommand jsonCommand);
    CommandProcessingResult undoApproveShareAccount(Long entityId, JsonCommand jsonCommand);
    CommandProcessingResult closeShareAccount(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult applyAddtionalShares(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult approveAdditionalShares(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult rejectAdditionalShares(Long accountId, JsonCommand jsonCommand);
    CommandProcessingResult redeemShares(Long accountId, JsonCommand jsonCommand);
}
