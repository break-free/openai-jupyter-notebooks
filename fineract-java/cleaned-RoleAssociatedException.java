
package org.apache.fineract.useradministration.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class RoleAssociatedException extends AbstractPlatformDomainRuleException {
    public RoleAssociatedException(final String errorcode, final Long id) {
        super(errorcode, "Role with identifier " + id + " associated with users", id);
    }
}
