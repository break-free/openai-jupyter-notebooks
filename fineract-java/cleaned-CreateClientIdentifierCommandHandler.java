
package org.apache.fineract.portfolio.client.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.service.ClientIdentifierWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CLIENTIDENTIFIER", action = "CREATE")
public class CreateClientIdentifierCommandHandler implements NewCommandSourceHandler {
    private final ClientIdentifierWritePlatformService clientIdentifierWritePlatformService;
    @Autowired
    public CreateClientIdentifierCommandHandler(final ClientIdentifierWritePlatformService clientIdentifierWritePlatformService) {
        this.clientIdentifierWritePlatformService = clientIdentifierWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.clientIdentifierWritePlatformService.addClientIdentifier(command.getClientId(), command);
    }
}
