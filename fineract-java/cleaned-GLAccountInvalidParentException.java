
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountInvalidParentException extends AbstractPlatformDomainRuleException {
    public GLAccountInvalidParentException(final long glAccountId) {
        super("error.msg.glaccount.parent.invalid",
                "The account with id " + glAccountId + " is a 'Detail' account and cannot be used as a parent", glAccountId);
    }
}
