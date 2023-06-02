
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class LoanRescheduleRequestNotFoundException extends AbstractPlatformResourceNotFoundException {
    public LoanRescheduleRequestNotFoundException(final Long requestId) {
        super("error.msg.loan.reschedule.request.id.invalid", "Loan reschedule request with identifier " + requestId + " does not exist",
                requestId);
    }
}
