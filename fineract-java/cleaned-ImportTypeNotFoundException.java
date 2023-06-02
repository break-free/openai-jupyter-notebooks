
package org.apache.fineract.infrastructure.bulkimport.exceptions;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ImportTypeNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ImportTypeNotFoundException(final String entityType) {
        super("error.msg.entity.type.invalid", "Entity type " + entityType + " does not exist", entityType);
    }
}
