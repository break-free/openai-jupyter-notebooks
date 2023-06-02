
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class InvalidParentGLAccountHeadException extends AbstractPlatformDomainRuleException {
    public InvalidParentGLAccountHeadException(final Long glAccountId, final Long parentId) {
        super("error.msg.glaccount.id.and.parentid.must.not.same", "parentId:" + parentId + ", id" + glAccountId + " should not be same",
                glAccountId, parentId);
    }
}
