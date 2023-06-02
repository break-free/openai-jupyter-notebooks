
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class EntityDatatableCheckNotAllowException extends AbstractPlatformResourceNotFoundException {
    public EntityDatatableCheckNotAllowException(final String entityName) {
        super("error.msg.entity.datatable.check.is.not.allowed", "Entity Datatable check is not allow without a loan product id to :entity,"
                + " because there is already a check attached to the same entity with a loan product id", entityName);
    }
}
