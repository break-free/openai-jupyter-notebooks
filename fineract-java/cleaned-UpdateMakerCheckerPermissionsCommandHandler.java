
package org.apache.fineract.useradministration.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.useradministration.service.PermissionWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PERMISSION", action = "UPDATE")
public class UpdateMakerCheckerPermissionsCommandHandler implements NewCommandSourceHandler {
    private final PermissionWritePlatformService writePlatformService;
    @Autowired
    public UpdateMakerCheckerPermissionsCommandHandler(final PermissionWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateMakerCheckerPermissions(command);
    }
}
