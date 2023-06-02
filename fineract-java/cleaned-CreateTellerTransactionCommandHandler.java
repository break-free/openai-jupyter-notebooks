
package org.apache.fineract.organisation.teller.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.teller.service.TellerTransactionWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
public class CreateTellerTransactionCommandHandler implements NewCommandSourceHandler {
    private final TellerTransactionWritePlatformService writePlatformService;
    @Autowired
    public CreateTellerTransactionCommandHandler(final TellerTransactionWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createTellerTransaction(command);
    }
}
