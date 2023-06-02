
package org.apache.fineract.organisation.holiday.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.holiday.service.HolidayWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "HOLIDAY", action = "UPDATE")
public class UpdateHolidayCommandHandler implements NewCommandSourceHandler {
    private final HolidayWritePlatformService holidayWritePlatformService;
    @Autowired
    public UpdateHolidayCommandHandler(final HolidayWritePlatformService holidayWritePlatformService) {
        this.holidayWritePlatformService = holidayWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.holidayWritePlatformService.updateHoliday(command);
    }
}
