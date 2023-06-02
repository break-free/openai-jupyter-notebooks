
package org.apache.fineract.portfolio.client.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.service.ClientWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CLIENT", action = "WITHDRAW")
public class WithdrawClientCommandHandler implements NewCommandSourceHandler {
    private final ClientWritePlatformService clientWritePlatformService;
    @Autowired
    public WithdrawClientCommandHandler(final ClientWritePlatformService clientWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.clientWritePlatformService.withdrawClient(command.entityId(), command);
    }
}
