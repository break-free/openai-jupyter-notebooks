
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public final class LoanRepaymentScheduleInstallmentData {
    private Long id;
    private Integer installmentId;
    private LocalDate date;
    private BigDecimal amount;
    private LoanRepaymentScheduleInstallmentData(final Long id, final Integer installmentId, final LocalDate date,
            final BigDecimal amount) {
        this.amount = amount;
        this.date = date;
        this.installmentId = installmentId;
        this.id = id;
    }
    public static LoanRepaymentScheduleInstallmentData instanceOf(final Long id, final Integer installmentId, final LocalDate date,
            final BigDecimal amount) {
        return new LoanRepaymentScheduleInstallmentData(id, installmentId, date, amount);
    }
}
