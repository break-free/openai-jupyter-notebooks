
package org.apache.fineract.infrastructure.documentmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InvalidEntityTypeForDocumentManagementException extends AbstractPlatformResourceNotFoundException {
    public InvalidEntityTypeForDocumentManagementException(final String entityType) {
        super("error.documentmanagement.entitytype.invalid", "Document Management is not support for the Entity Type: " + entityType,
                entityType);
    }
}
