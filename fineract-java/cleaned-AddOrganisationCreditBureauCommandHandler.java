
package org.apache.fineract.infrastructure.creditbureau.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditbureau.service.OrganisationCreditBureauWritePlatflormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "ORGANISATIONCREDITBUREAU", action = "CREATE")
public class AddOrganisationCreditBureauCommandHandler implements NewCommandSourceHandler {
    private final OrganisationCreditBureauWritePlatflormService writePlatformService;
    @Autowired
    public AddOrganisationCreditBureauCommandHandler(final OrganisationCreditBureauWritePlatflormService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.addOrganisationCreditBureau(command.getOrganisationCreditBureauId(), command);
    }
}
