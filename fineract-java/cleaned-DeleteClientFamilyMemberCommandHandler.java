
package org.apache.fineract.portfolio.client.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.service.ClientFamilyMembersWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "FAMILYMEMBERS", action = "DELETE")
public class DeleteClientFamilyMemberCommandHandler implements NewCommandSourceHandler {
    private final ClientFamilyMembersWritePlatformService clientFamilyMembersWritePlatformService;
    @Autowired
    public DeleteClientFamilyMemberCommandHandler(final ClientFamilyMembersWritePlatformService clientFamilyMembersWritePlatformService) {
        this.clientFamilyMembersWritePlatformService = clientFamilyMembersWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.clientFamilyMembersWritePlatformService.deleteFamilyMember(command.entityId(), command);
    }
}
