
package org.apache.fineract.portfolio.group.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.group.service.GroupRolesWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GROUP", action = "ASSIGNROLE")
public class AssignRoleCommandHandler implements NewCommandSourceHandler {
    private final GroupRolesWritePlatformService groupRolesWritePlatformService;
    @Autowired
    public AssignRoleCommandHandler(final GroupRolesWritePlatformService groupRolesWritePlatformService) {
        this.groupRolesWritePlatformService = groupRolesWritePlatformService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.groupRolesWritePlatformService.createRole(command);
    }
}
