
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountInvalidUpdateException extends AbstractPlatformDomainRuleException {
    public enum GlAccountInvalidUpdateReason {
        TRANSANCTIONS_LOGGED;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("TRANSANCTIONS_LOGGED")) {
                return "This Usage of this (detail) GL Account as it already has transactions logged against it";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("TRANSANCTIONS_LOGGED")) {
                return "error.msg.glaccount.glcode.invalid.update.transactions.logged";
            }
            return name().toString();
        }
    }
    public GLAccountInvalidUpdateException(final GlAccountInvalidUpdateReason reason, final Long glAccountId) {
        super(reason.errorCode(), reason.errorMessage(), glAccountId);
    }
}
