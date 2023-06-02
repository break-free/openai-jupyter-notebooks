
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
@CommandType(entity = "CENTER", action = "DISASSOCIATEGROUPS")
public class DisassociateGroupsFromCenterCommandHandler implements NewCommandSourceHandler {
    private final GroupingTypesWritePlatformService writePlatformService;
    @Autowired
    public DisassociateGroupsFromCenterCommandHandler(final GroupingTypesWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.disassociateGroupsToCenter(command.entityId(), command);
    }
}
