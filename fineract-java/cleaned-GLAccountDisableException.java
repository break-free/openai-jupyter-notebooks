
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountDisableException extends AbstractPlatformDomainRuleException {
    public GLAccountDisableException() {
        super("error.msg.glaccount.attached.to.product", "General Ledger Account is already attached to product.");
    }
}
