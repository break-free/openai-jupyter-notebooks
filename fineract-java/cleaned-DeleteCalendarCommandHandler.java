
package org.apache.fineract.portfolio.calendar.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.calendar.service.CalendarWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CALENDAR", action = "DELETE")
public class DeleteCalendarCommandHandler implements NewCommandSourceHandler {
    private final CalendarWritePlatformService writePlatformService;
    @Autowired
    public DeleteCalendarCommandHandler(final CalendarWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.deleteCalendar(command.entityId());
    }
}
