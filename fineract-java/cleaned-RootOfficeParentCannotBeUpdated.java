
package org.apache.fineract.organisation.office.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class RootOfficeParentCannotBeUpdated extends AbstractPlatformDomainRuleException {
    public RootOfficeParentCannotBeUpdated() {
        super("error.msg.office.cannot.update.parent.office.of.root.office", "The root office must not be set with a parent office.");
    }
}
