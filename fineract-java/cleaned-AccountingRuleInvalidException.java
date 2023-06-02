
package org.apache.fineract.accounting.rule.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class AccountingRuleInvalidException extends AbstractPlatformDomainRuleException {
    public enum GlClosureInvalidReason {
        FUTURE_DATE, ACCOUNTING_CLOSED;
        public String errorMessage() {
            if (name().toString().equalsIgnoreCase("FUTURE_DATE")) {
                return "Accounting closures cannot be made for a future date";
            } else if (name().toString().equalsIgnoreCase("ACCOUNTING_CLOSED")) {
                return "Accounting Closure for this branch has already been defined for a greater date";
            }
            return name().toString();
        }
        public String errorCode() {
            if (name().toString().equalsIgnoreCase("FUTURE_DATE")) {
                return "error.msg.glclosure.invalid.future.date";
            } else if (name().toString().equalsIgnoreCase("ACCOUNTING_CLOSED")) {
                return "error.msg.glclosure.invalid.accounting.closed";
            }
            return name().toString();
        }
    }
    public AccountingRuleInvalidException(final GlClosureInvalidReason reason, final LocalDate date) {
        super(reason.errorCode(), reason.errorMessage(), date);
    }
}
