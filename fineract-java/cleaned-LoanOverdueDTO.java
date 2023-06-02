
package org.apache.fineract.portfolio.loanproduct.data;
import java.time.LocalDate;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public class LoanOverdueDTO {
    private final Loan loan;
    private final boolean runInterestRecalculation;
    private final LocalDate recalculateFrom;
    private final LocalDate lastChargeAppliedDate;
    public LoanOverdueDTO(final Loan loan, final boolean runInterestRecalculation, final LocalDate recalculateFrom,
            final LocalDate lastChargeAppliedDate) {
        this.loan = loan;
        this.runInterestRecalculation = runInterestRecalculation;
        this.recalculateFrom = recalculateFrom;
        this.lastChargeAppliedDate = lastChargeAppliedDate;
    }
    public boolean isRunInterestRecalculation() {
        return this.runInterestRecalculation;
    }
    public Loan getLoan() {
        return this.loan;
    }
    public LocalDate getRecalculateFrom() {
        return this.recalculateFrom;
    }
    public LocalDate getLastChargeAppliedDate() {
        return this.lastChargeAppliedDate;
    }
}
