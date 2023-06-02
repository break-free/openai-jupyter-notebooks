
package org.apache.fineract.infrastructure.creditbureau.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditbureau.service.CreditReportWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "CREDITREPORT", action = "DELETE")
public class DeleteCreditReportCommandHandler implements NewCommandSourceHandler {
    private final CreditReportWritePlatformService writePlatformService;
    @Autowired
    public DeleteCreditReportCommandHandler(final CreditReportWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.deleteCreditReport(command.entityId(), command);
    }
}
