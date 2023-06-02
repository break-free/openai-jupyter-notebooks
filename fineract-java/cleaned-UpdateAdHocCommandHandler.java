
package org.apache.fineract.adhocquery.handler;
import org.apache.fineract.adhocquery.service.AdHocWritePlatformService;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "ADHOC", action = "UPDATE")
public class UpdateAdHocCommandHandler implements NewCommandSourceHandler {
    private final AdHocWritePlatformService writePlatformService;
    @Autowired
    public UpdateAdHocCommandHandler(final AdHocWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        final Long adHocId = command.entityId();
        return this.writePlatformService.updateAdHocQuery(adHocId, command);
    }
}
