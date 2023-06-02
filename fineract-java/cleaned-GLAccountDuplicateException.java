
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountDuplicateException extends AbstractPlatformDomainRuleException {
    public GLAccountDuplicateException(final String glCode) {
        super("error.msg.glaccount.glcode.duplicate", "General Ledger Account with GL code " + glCode + " is already present", glCode);
    }
}
