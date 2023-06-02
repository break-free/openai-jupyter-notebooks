
package org.apache.fineract.portfolio.note.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class NoteResourceNotSupportedException extends AbstractPlatformResourceNotFoundException {
    public NoteResourceNotSupportedException(final String resource) {
        super("error.msg.note.resource.not.supported", "Note does not support resource " + resource);
    }
}
