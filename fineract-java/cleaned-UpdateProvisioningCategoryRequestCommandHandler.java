
package org.apache.fineract.organisation.provisioning.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.provisioning.service.ProvisioningCategoryWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PROVISIONCATEGORY", action = "UPDATE")
public class UpdateProvisioningCategoryRequestCommandHandler implements NewCommandSourceHandler {
    private final ProvisioningCategoryWritePlatformService provisioningCategoryWritePlatformService;
    @Autowired
    public UpdateProvisioningCategoryRequestCommandHandler(
            ProvisioningCategoryWritePlatformService provisioningCategoryWritePlatformService) {
        this.provisioningCategoryWritePlatformService = provisioningCategoryWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.provisioningCategoryWritePlatformService.updateProvisioningCategory(jsonCommand.entityId(), jsonCommand);
    }
}
