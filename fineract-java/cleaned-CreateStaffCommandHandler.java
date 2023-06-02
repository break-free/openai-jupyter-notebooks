
package org.apache.fineract.organisation.staff.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.staff.service.StaffWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "STAFF", action = "CREATE")
public class CreateStaffCommandHandler implements NewCommandSourceHandler {
    private final StaffWritePlatformService writePlatformService;
    @Autowired
    public CreateStaffCommandHandler(final StaffWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.createStaff(command);
    }
}
