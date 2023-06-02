
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountInvalidUsageException extends AbstractPlatformDomainRuleException {
    public GLAccountInvalidUsageException(final Integer type) {
        super("error.msg.glaccount.classification.invalid", "The following COA type is invalid: " + type);
    }
}
