
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
@CommandType(entity = "GROUP", action = "CLOSE")
public class CloseGroupCommandHandler implements NewCommandSourceHandler {
    private final GroupingTypesWritePlatformService groupingTypesWritePlatformService;
    @Autowired
    public CloseGroupCommandHandler(final GroupingTypesWritePlatformService groupingTypesWritePlatformService) {
        this.groupingTypesWritePlatformService = groupingTypesWritePlatformService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.groupingTypesWritePlatformService.closeGroup(command.entityId(), command);
    }
}
