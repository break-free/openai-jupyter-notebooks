
package org.apache.fineract.organisation.office.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.office.service.OfficeWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "OFFICETRANSACTION", action = "CREATE")
public class CreateOfficeTransactionCommandHandler implements NewCommandSourceHandler {
    private final OfficeWritePlatformService writePlatformService;
    @Autowired
    public CreateOfficeTransactionCommandHandler(final OfficeWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.officeTransaction(command);
    }
}
