
package org.apache.fineract.portfolio.shareaccounts.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.shareaccounts.service.ShareAccountWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "SHAREACCOUNT", action = "UNDOAPPROVAL")
public class UndoApproveShareAccountCommandHandler implements NewCommandSourceHandler {
    private final ShareAccountWritePlatformService shareAccountWritePlatformService;
    @Autowired
    public UndoApproveShareAccountCommandHandler(final ShareAccountWritePlatformService shareAccountWritePlatformService) {
        this.shareAccountWritePlatformService = shareAccountWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.shareAccountWritePlatformService.undoApproveShareAccount(jsonCommand.entityId(), jsonCommand);
    }
}
