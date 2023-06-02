
package org.apache.fineract.infrastructure.dataqueries.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.dataqueries.service.ReportWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "REPORT", action = "DELETE")
public class DeleteReportCommandHandler implements NewCommandSourceHandler {
    private final ReportWritePlatformService writePlatformService;
    @Autowired
    public DeleteReportCommandHandler(final ReportWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        this.writePlatformService.deleteReport(command.entityId());
        return new CommandProcessingResultBuilder() 
                .withCommandId(command.commandId()) 
                .withEntityId(command.entityId()) 
                .build();
    }
}
