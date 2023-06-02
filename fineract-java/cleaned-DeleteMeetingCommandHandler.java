
package org.apache.fineract.portfolio.meeting.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.meeting.service.MeetingWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "MEETING", action = "DELETE")
public class DeleteMeetingCommandHandler implements NewCommandSourceHandler {
    private final MeetingWritePlatformService writePlatformService;
    @Autowired
    public DeleteMeetingCommandHandler(final MeetingWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.deleteMeeting(command.entityId());
    }
}
