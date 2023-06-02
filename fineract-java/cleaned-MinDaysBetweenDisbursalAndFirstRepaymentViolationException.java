
package org.apache.fineract.portfolio.loanaccount.exception;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class MinDaysBetweenDisbursalAndFirstRepaymentViolationException extends AbstractPlatformDomainRuleException {
    public MinDaysBetweenDisbursalAndFirstRepaymentViolationException(final LocalDate disbursalDate, final LocalDate firstRepaymentDate,
            Integer minimumDaysBetweenDisbursalAndFirstRepayment) {
        super("error.msg.loan.days.between.first.repayment.and.disbursal.are.less.than.minimum.allowed",
                "Number of days between loan disbursal  (" + disbursalDate + ") and first repayment (" + firstRepaymentDate
                        + ") can't be less than (" + minimumDaysBetweenDisbursalAndFirstRepayment + ").",
                disbursalDate, firstRepaymentDate, minimumDaysBetweenDisbursalAndFirstRepayment);
    }
}
