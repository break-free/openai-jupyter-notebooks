
package org.apache.fineract.module.service.custom;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.note.service.NoteWritePlatformService;
import org.springframework.stereotype.Service;
@Service
public class CustomNoteWritePlatformService implements NoteWritePlatformService {
    @Override
    public CommandProcessingResult createNote(JsonCommand command) {
        return null;
    }
    @Override
    public CommandProcessingResult updateNote(JsonCommand command) {
        return null;
    }
    @Override
    public CommandProcessingResult deleteNote(JsonCommand command) {
        return null;
    }
    @Override
    public void createAndPersistClientNote(Client client, JsonCommand command) {
    }
}
