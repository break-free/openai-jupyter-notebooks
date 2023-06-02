
package org.apache.fineract.accounting.closure.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GLClosureDuplicateException extends AbstractPlatformDomainRuleException {
    public GLClosureDuplicateException(final Long officeId, final LocalDate closureDate) {
        super("error.msg.glclosure.glcode.duplicate",
                "An accounting closure for branch with Id " + officeId + " already exists for the date " + closureDate, officeId,
                closureDate);
    }
}
