
package org.apache.fineract.portfolio.shareproducts.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.shareproducts.service.ShareProductWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "SHAREPRODUCT", action = "APPROVE_DIVIDEND")
public class ApproveShareProductDividendCommandHandler implements NewCommandSourceHandler {
    private final ShareProductWritePlatformService shareProductWritePlatformService;
    @Autowired
    public ApproveShareProductDividendCommandHandler(final ShareProductWritePlatformService shareProductWritePlatformService) {
        this.shareProductWritePlatformService = shareProductWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.shareProductWritePlatformService.approveShareProductDividend(jsonCommand.entityId());
    }
}
