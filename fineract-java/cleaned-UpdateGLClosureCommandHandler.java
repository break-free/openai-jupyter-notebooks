
package org.apache.fineract.accounting.closure.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.closure.service.GLClosureWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GLCLOSURE", action = "UPDATE")
@RequiredArgsConstructor
public class UpdateGLClosureCommandHandler implements NewCommandSourceHandler {
    private final GLClosureWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateGLClosure(command.entityId(), command);
    }
}
