
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class EntityDatatableChecksNotFoundException extends AbstractPlatformResourceNotFoundException {
    public EntityDatatableChecksNotFoundException(final Long id) {
        super("error.msg.entity.datatable.check.not.found", "Entity datatable check with identifier " + id + " does not exist", id);
    }
}
