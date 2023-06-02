
package org.apache.fineract.organisation.teller.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.teller.service.CashierWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
public class ModifyCashierCommandHandler implements NewCommandSourceHandler {
    private final CashierWritePlatformService writePlatformService;
    @Autowired
    public ModifyCashierCommandHandler(final CashierWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.modifyCashier(command.entityId(), command);
    }
}
