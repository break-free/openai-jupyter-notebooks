
package org.apache.fineract.portfolio.self.pockets.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.self.pockets.api.PocketApiConstants;
import org.apache.fineract.portfolio.self.pockets.service.PocketWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = PocketApiConstants.pocketEntityName, action = PocketApiConstants.delinkAccountsActionName)
public class DelinkAccountsFromPocketCommandHandler implements NewCommandSourceHandler {
    private final PocketWritePlatformService pocketWritePlatformService;
    @Autowired
    public DelinkAccountsFromPocketCommandHandler(final PocketWritePlatformService pocketWritePlatformService) {
        this.pocketWritePlatformService = pocketWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.pocketWritePlatformService.delinkAccounts(command);
    }
}
