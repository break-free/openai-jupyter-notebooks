
package org.apache.fineract.infrastructure.entityaccess.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.entityaccess.service.FineractEntityAccessWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ENTITYMAPPING", action = "CREATE")
public class CreateEntityToEntityMappingCommandHandler implements NewCommandSourceHandler {
    private final FineractEntityAccessWriteService fineractEntityAccessWriteService;
    @Autowired
    public CreateEntityToEntityMappingCommandHandler(final FineractEntityAccessWriteService fineractEntityAccessWriteService) {
        this.fineractEntityAccessWriteService = fineractEntityAccessWriteService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.fineractEntityAccessWriteService.createEntityToEntityMapping(command.entityId(), command);
    }
}
