
package org.apache.fineract.infrastructure.creditbureau.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditbureau.service.OrganisationCreditBureauWritePlatflormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ORGANISATIONCREDITBUREAU", action = "UPDATE")
public class UpdateCreditBureauCommandHandler implements NewCommandSourceHandler {
    private final OrganisationCreditBureauWritePlatflormService writePlatformService;
    @Autowired
    public UpdateCreditBureauCommandHandler(final OrganisationCreditBureauWritePlatflormService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateCreditBureau(command);
    }
}
