
package org.apache.fineract.portfolio.note.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.domain.Client;
public interface NoteWritePlatformService {
    CommandProcessingResult createNote(JsonCommand command);
    CommandProcessingResult updateNote(JsonCommand command);
    CommandProcessingResult deleteNote(JsonCommand command);
    void createAndPersistClientNote(Client client, JsonCommand command);
}
