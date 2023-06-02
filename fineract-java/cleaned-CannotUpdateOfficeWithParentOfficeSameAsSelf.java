
package org.apache.fineract.organisation.office.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class CannotUpdateOfficeWithParentOfficeSameAsSelf extends AbstractPlatformDomainRuleException {
    public CannotUpdateOfficeWithParentOfficeSameAsSelf(final Long officeId, final Long parentId) {
        super("error.msg.office.parentId.same.as.id", "Cannot update office with parent same as self.", officeId, parentId);
    }
}
