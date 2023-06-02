
package org.apache.fineract.infrastructure.creditbureau.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditbureau.service.CreditBureauConfigurationWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CREDITBUREAU_CONFIGURATION", action = "CREATE")
public class AddCreditBureauConfigurationDataCommandHandler implements NewCommandSourceHandler {
    private final CreditBureauConfigurationWritePlatformService writePlatformService;
    @Autowired
    public AddCreditBureauConfigurationDataCommandHandler(final CreditBureauConfigurationWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.addCreditBureauConfiguration(command.entityId(), command);
    }
}
