
package org.apache.fineract.infrastructure.dataqueries.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.dataqueries.service.ReadWriteNonCoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CreateDatatableEntryCommandHandler implements NewCommandSourceHandler {
    private final ReadWriteNonCoreDataService writePlatformService;
    @Autowired
    public CreateDatatableEntryCommandHandler(final ReadWriteNonCoreDataService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createNewDatatableEntry(command.entityName(), command.entityId(), command);
    }
}
