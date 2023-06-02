
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DatatableEntryRequiredException extends AbstractPlatformDomainRuleException {
    public DatatableEntryRequiredException(String datatableName) {
        super("error.msg.entry.required.in.datatable." + datatableName,
                "The datatable " + datatableName + " needs to be filled in before the current action can be proceeded", datatableName);
    }
    public DatatableEntryRequiredException(String datatableName, Long appTableId) {
        super("error.msg.entry.cannot.be.deleted.datatable." + datatableName + ".attached.to.entity.datatable.check",
                "The entry cannot be deleted, due to datatable " + datatableName + " is attached to an Entity-Datatable check",
                datatableName, appTableId);
    }
}
