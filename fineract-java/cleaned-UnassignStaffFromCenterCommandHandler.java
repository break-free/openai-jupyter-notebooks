
package org.apache.fineract.portfolio.group.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.group.service.GroupingTypesWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UnassignStaffFromCenterCommandHandler implements NewCommandSourceHandler {
    private final GroupingTypesWritePlatformService writePlatformService;
    @Autowired
    public UnassignStaffFromCenterCommandHandler(final GroupingTypesWritePlatformService groupWritePlatformService) {
        this.writePlatformService = groupWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.unassignGroupOrCenterStaff(command.entityId(), command);
    }
}
