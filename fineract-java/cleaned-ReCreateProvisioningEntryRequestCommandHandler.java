
package org.apache.fineract.accounting.provisioning.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.provisioning.service.ProvisioningEntriesWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PROVISIONENTRIES", action = "RECREATE")
@RequiredArgsConstructor
public class ReCreateProvisioningEntryRequestCommandHandler implements NewCommandSourceHandler {
    private final ProvisioningEntriesWritePlatformService provisioningEntriesWritePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.provisioningEntriesWritePlatformService.reCreateProvisioningEntries(jsonCommand.entityId(), jsonCommand);
    }
}
