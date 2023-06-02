
package org.apache.fineract.infrastructure.dataqueries.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.dataqueries.service.ReadWriteNonCoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DeleteOneToManyDatatableEntryCommandHandler implements NewCommandSourceHandler {
    private final ReadWriteNonCoreDataService writePlatformService;
    @Autowired
    public DeleteOneToManyDatatableEntryCommandHandler(final ReadWriteNonCoreDataService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        final CommandProcessingResult commandProcessingResult = this.writePlatformService.deleteDatatableEntry(command.entityName(),
                command.entityId(), command.subentityId());
        return new CommandProcessingResultBuilder() 
                .withCommandId(command.commandId()) 
                .withEntityId(command.entityId()) 
                .withOfficeId(commandProcessingResult.getOfficeId()) 
                .withGroupId(commandProcessingResult.getGroupId()) 
                .withClientId(commandProcessingResult.getClientId()) 
                .withSavingsId(commandProcessingResult.getSavingsId()) 
                .withLoanId(commandProcessingResult.getLoanId()) 
                .build();
    }
}
