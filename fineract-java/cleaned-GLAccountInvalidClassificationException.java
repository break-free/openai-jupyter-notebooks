
package org.apache.fineract.accounting.glaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLAccountInvalidClassificationException extends AbstractPlatformDomainRuleException {
    public GLAccountInvalidClassificationException(final Integer usage) {
        super("error.msg.glaccount.usage.invalid", "The following COA usage is invalid: " + usage);
    }
}
