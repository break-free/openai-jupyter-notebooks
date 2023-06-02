
package org.apache.fineract.portfolio.note.handler;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.note.service.NoteWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UpdateNoteCommandHandler implements NewCommandSourceHandler {
    private final NoteWritePlatformService writePlatformService;
    @Autowired
    public UpdateNoteCommandHandler(final NoteWritePlatformService noteWritePlatformService) {
        this.writePlatformService = noteWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateNote(command);
    }
}
