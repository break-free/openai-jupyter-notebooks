
package org.apache.fineract.portfolio.note.service;
import java.util.Collection;
import org.apache.fineract.portfolio.note.data.NoteData;
public interface NoteReadPlatformService {
    NoteData retrieveNote(Long noteId, Long resourceId, Integer noteTypeId);
    Collection<NoteData> retrieveNotesByResource(Long resourceId, Integer noteTypeId);
}
