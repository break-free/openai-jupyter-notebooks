
package org.apache.fineract.portfolio.group.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.group.service.GroupingTypesWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GROUP", action = "ASSOCIATECLIENTS")
public class AssociateClientsToGroupCommandHandler implements NewCommandSourceHandler {
    private final GroupingTypesWritePlatformService writePlatformService;
    @Autowired
    public AssociateClientsToGroupCommandHandler(final GroupingTypesWritePlatformService clientWritePlatformService) {
        this.writePlatformService = clientWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.associateClientsToGroup(command.entityId(), command);
    }
}
