
package org.apache.fineract.infrastructure.documentmanagement.exception;
import com.amazonaws.AmazonClientException;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class DocumentNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DocumentNotFoundException(final String entityType, final Long entityId, final Long id) {
        super("error.msg.document.id.invalid",
                "Document with identifier " + id + " does not exist for the " + entityType + " with Identifier " + entityId, id);
    }
    public DocumentNotFoundException(String entityType, Long entityId, Long id, EmptyResultDataAccessException e) {
        super("error.msg.document.id.invalid",
                "Document with identifier " + id + " does not exist for the " + entityType + " with Identifier " + entityId, id, e);
    }
    public DocumentNotFoundException(String entityType, Long entityId, Long id, AmazonClientException ace) {
        super("error.msg.document.id.invalid",
                "Document with identifier " + id + " does not exist for the " + entityType + " with Identifier " + entityId, id, ace);
    }
}
