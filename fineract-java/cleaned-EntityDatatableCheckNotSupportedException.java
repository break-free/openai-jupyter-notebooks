
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class EntityDatatableCheckNotSupportedException extends AbstractPlatformResourceNotFoundException {
    public EntityDatatableCheckNotSupportedException(final String datatableName, final String entityName) {
        super("error.msg.entity.datatable.check.combination.not.supported",
                "Entity Datatable check on table " + datatableName + " is not supported for entity " + entityName, datatableName,
                entityName);
    }
    public EntityDatatableCheckNotSupportedException(final String entityName, final Long productId) {
        super("error.msg.entity.datatable.check.combination.not.supported",
                "Entity Datatable check on entity " + entityName + " is not supported with productId " + productId, entityName, productId);
    }
}
