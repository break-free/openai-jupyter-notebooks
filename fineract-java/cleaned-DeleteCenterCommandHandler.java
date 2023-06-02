
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
@CommandType(entity = "CENTER", action = "DELETE")
public class DeleteCenterCommandHandler implements NewCommandSourceHandler {
    private final GroupingTypesWritePlatformService groupWritePlatformService;
    @Autowired
    public DeleteCenterCommandHandler(final GroupingTypesWritePlatformService groupWritePlatformService) {
        this.groupWritePlatformService = groupWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.groupWritePlatformService.deleteGroup(command.entityId());
    }
}
