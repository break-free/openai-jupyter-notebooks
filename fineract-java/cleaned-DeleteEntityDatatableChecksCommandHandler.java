
package org.apache.fineract.infrastructure.dataqueries.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.dataqueries.service.EntityDatatableChecksWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ENTITY_DATATABLE_CHECK", action = "DELETE")
public class DeleteEntityDatatableChecksCommandHandler implements NewCommandSourceHandler {
    private final EntityDatatableChecksWritePlatformService writePlatformService;
    @Autowired
    public DeleteEntityDatatableChecksCommandHandler(final EntityDatatableChecksWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.deleteCheck(command.entityId());
    }
}
