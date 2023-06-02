
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountInvalidDeleteException extends AbstractPlatformDomainRuleException {
    public enum GlAccountInvalidDeleteReason {
        TRANSANCTIONS_LOGGED, HAS_CHILDREN;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("TRANSANCTIONS_LOGGED")) {
                return "This GL Account cannot be deleted as it has transactions logged against it";
            } else if (name().toString().equalsIgnoreCase("HAS_CHILDREN")) {
                return "Cannot delete this Header GL Account without first deleting or reassinging its children";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("TRANSANCTIONS_LOGGED")) {
                return "error.msg.glaccount.glcode.invalid.delete.transactions.logged";
            } else if (name().toString().equalsIgnoreCase("HAS_CHILDREN")) {
                return "error.msg.glaccount.glcode.invalid.delete.has.children";
            }
            return name().toString();
        }
    }
    public GLAccountInvalidDeleteException(final GlAccountInvalidDeleteReason reason, final Long glAccountId) {
        super(reason.errorCode(), reason.errorMessage(), glAccountId);
    }
}
