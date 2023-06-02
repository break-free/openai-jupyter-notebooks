
package org.apache.fineract.infrastructure.jobs.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.service.SchedularWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "SCHEDULER", action = "UPDATE")
public class UpdateJobDetailCommandhandler implements NewCommandSourceHandler {
    private final SchedularWritePlatformService schedularWritePlatformService;
    @Autowired
    public UpdateJobDetailCommandhandler(final SchedularWritePlatformService schedularWritePlatformService) {
        this.schedularWritePlatformService = schedularWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.schedularWritePlatformService.updateJobDetail(command.entityId(), command);
    }
}
