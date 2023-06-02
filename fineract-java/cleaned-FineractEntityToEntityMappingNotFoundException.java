
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class FineractEntityToEntityMappingNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FineractEntityToEntityMappingNotFoundException(final String id) {
        super("error.msg.entityaccess.id.invalid", "FineractEntityToEntityMapping with identifier " + id + " does not exist", id);
    }
}
