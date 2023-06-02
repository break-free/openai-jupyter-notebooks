
package org.apache.fineract.module.service.custom;
import java.util.Collection;
import org.apache.fineract.portfolio.note.data.NoteData;
import org.apache.fineract.portfolio.note.service.NoteReadPlatformService;
import org.springframework.stereotype.Service;
@Service
public class CustomNoteReadPlatformService implements NoteReadPlatformService {
    @Override
    public NoteData retrieveNote(Long noteId, Long resourceId, Integer noteTypeId) {
        return null;
    }
    @Override
    public Collection<NoteData> retrieveNotesByResource(Long resourceId, Integer noteTypeId) {
        return null;
    }
}
