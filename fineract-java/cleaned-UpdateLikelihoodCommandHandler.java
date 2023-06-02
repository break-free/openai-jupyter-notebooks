
package org.apache.fineract.infrastructure.survey.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.survey.service.WriteLikelihoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "LIKELIHOOD", action = "UPDATE")
public class UpdateLikelihoodCommandHandler implements NewCommandSourceHandler {
    private final WriteLikelihoodService writePlatformService;
    @Autowired
    public UpdateLikelihoodCommandHandler(final WriteLikelihoodService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.update(command.entityId(), command);
    }
}
