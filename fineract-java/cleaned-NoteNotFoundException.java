
package org.apache.fineract.portfolio.note.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class NoteNotFoundException extends AbstractPlatformResourceNotFoundException {
    public NoteNotFoundException(final Long id) {
        super("error.msg.note.id.invalid", "Note with identifier " + id + " does not exist", id);
    }
    public NoteNotFoundException(final Long id, final Long resourceId, final String resource) {
        super("error.msg." + resource + ".note.id.invalid",
                "Note with identifier " + id + " does not exist for " + resource + " with identifier " + resourceId, id, resourceId);
    }
    public NoteNotFoundException(Long id, Long resourceId, String resource, EmptyResultDataAccessException e) {
        super("error.msg." + resource + ".note.id.invalid",
                "Note with identifier " + id + " does not exist for " + resource + " with identifier " + resourceId, id, resourceId, e);
    }
}
