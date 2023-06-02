
package org.apache.fineract.portfolio.loanaccount.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DateMismatchException extends AbstractPlatformDomainRuleException {
    public DateMismatchException(final LocalDate actualDisbursementDate, final LocalDate expectedDisbursedOnLocalDate) {
        super("error.msg.actual.disbursement.date.does.not.match.with.expected.disbursal.date", "Actual disbursement date  ("
                + actualDisbursementDate + ") " + "should be equal to Expected disbursal date (" + expectedDisbursedOnLocalDate + ")",
                actualDisbursementDate, expectedDisbursedOnLocalDate, null);
    }
}
