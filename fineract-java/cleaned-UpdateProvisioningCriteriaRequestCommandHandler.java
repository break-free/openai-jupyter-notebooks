
package org.apache.fineract.organisation.provisioning.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.provisioning.service.ProvisioningCriteriaWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PROVISIONCRITERIA", action = "UPDATE")
public class UpdateProvisioningCriteriaRequestCommandHandler implements NewCommandSourceHandler {
    private final ProvisioningCriteriaWritePlatformService provisioningCriteriaWritePlatformService;
    @Autowired
    public UpdateProvisioningCriteriaRequestCommandHandler(
            final ProvisioningCriteriaWritePlatformService provisioningCriteriaWritePlatformService) {
        this.provisioningCriteriaWritePlatformService = provisioningCriteriaWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.provisioningCriteriaWritePlatformService.updateProvisioningCriteria(jsonCommand.entityId(), jsonCommand);
    }
}
