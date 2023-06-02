
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class EntityDatatableCheckAlreadyExistsException extends AbstractPlatformDomainRuleException {
    public EntityDatatableCheckAlreadyExistsException(final String entityName, final Long status, final String datatableName) {
        super("error.msg.entityDatatableCheck.duplicate.entry", "the entity datatable check for status: '" + status
                + "' and datatable name '" + datatableName + "' on entity '" + entityName + "' already exist", "status", "datatableName",
                "entity", status, datatableName, entityName);
    }
    public EntityDatatableCheckAlreadyExistsException(final String entityName, long status, String datatableName, long productId) {
        super("error.msg.entityDatatableCheck.duplicate.entry",
                "the entity datatable check for status: '" + status + "' and datatable name '" + datatableName + "' on entity '"
                        + entityName + "' and product id '" + productId + "' already exist",
                "status", "datatableName", "entity", "productId", status, datatableName, entityName, productId);
    }
}
