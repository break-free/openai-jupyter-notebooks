
package org.apache.fineract.organisation.workingdays.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.workingdays.service.WorkingDaysWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "WORKINGDAYS", action = "UPDATE")
public class UpdateWorkingDaysCommandHandler implements NewCommandSourceHandler {
    private final WorkingDaysWritePlatformService workingDaysWritePlatformService;
    @Autowired
    public UpdateWorkingDaysCommandHandler(final WorkingDaysWritePlatformService workingDaysWritePlatformService) {
        this.workingDaysWritePlatformService = workingDaysWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.workingDaysWritePlatformService.updateWorkingDays(command);
    }
}
