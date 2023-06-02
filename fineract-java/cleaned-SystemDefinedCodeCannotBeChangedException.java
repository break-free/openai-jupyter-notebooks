
package org.apache.fineract.infrastructure.codes.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SystemDefinedCodeCannotBeChangedException extends AbstractPlatformDomainRuleException {
    public SystemDefinedCodeCannotBeChangedException() {
        super("error.msg.code.systemdefined", "This code is system defined and cannot be modified or deleted.");
    }
}
