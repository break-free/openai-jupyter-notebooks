
package org.apache.fineract.accounting.glaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.service.GLAccountWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GLACCOUNT", action = "DELETE")
@RequiredArgsConstructor
public class DeleteGLAccountCommandHandler implements NewCommandSourceHandler {
    private final GLAccountWritePlatformService writePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.deleteGLAccount(command.entityId());
    }
}
