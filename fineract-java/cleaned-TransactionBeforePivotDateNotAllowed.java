
package org.apache.fineract.portfolio.savings.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class TransactionBeforePivotDateNotAllowed extends AbstractPlatformDomainRuleException {
    public TransactionBeforePivotDateNotAllowed(final LocalDate date, final LocalDate interestPostingDate) {
        super("error.msg.savings.transaction.is.not.allowed",
                "Savings Account transaction date " + date + " should be after the last interest posting date " + interestPostingDate);
    }
}
