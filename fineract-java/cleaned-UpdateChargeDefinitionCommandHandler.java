
package org.apache.fineract.portfolio.charge.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.charge.service.ChargeWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CHARGE", action = "UPDATE")
public class UpdateChargeDefinitionCommandHandler implements NewCommandSourceHandler {
    private final ChargeWritePlatformService clientWritePlatformService;
    @Autowired
    public UpdateChargeDefinitionCommandHandler(final ChargeWritePlatformService clientWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.clientWritePlatformService.updateCharge(command.entityId(), command);
    }
}
