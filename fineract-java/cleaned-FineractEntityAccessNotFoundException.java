
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class FineractEntityAccessNotFoundException extends AbstractPlatformResourceNotFoundException {
    public FineractEntityAccessNotFoundException(final Long id) {
        super("error.msg.entityaccess.id.invalid", "FineractEntityAccess with identifier " + id + " does not exist", id);
    }
    public FineractEntityAccessNotFoundException(final String codeName) {
        super("error.msg.entityaccess.id.invalid", "FineractEntityAccess with identifier " + codeName + " does not exist");
    }
}
