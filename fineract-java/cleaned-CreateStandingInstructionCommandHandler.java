
package org.apache.fineract.portfolio.account.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.account.service.StandingInstructionWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "STANDINGINSTRUCTION", action = "CREATE")
public class CreateStandingInstructionCommandHandler implements NewCommandSourceHandler {
    private StandingInstructionWritePlatformService standingInstructionWritePlatformService;
    @Autowired
    public CreateStandingInstructionCommandHandler(StandingInstructionWritePlatformService standingInstructionWritePlatformService) {
        this.standingInstructionWritePlatformService = standingInstructionWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.standingInstructionWritePlatformService.create(command);
    }
}
