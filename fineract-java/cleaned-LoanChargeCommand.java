
package org.apache.fineract.portfolio.loanaccount.command;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCharge;
public class LoanChargeCommand implements Comparable<LoanChargeCommand> {
    @SuppressWarnings("unused")
    private final Long id;
    private final Long chargeId;
    private final BigDecimal amount;
    @SuppressWarnings("unused")
    private final Integer chargeTimeType;
    @SuppressWarnings("unused")
    private final Integer chargeCalculationType;
    @SuppressWarnings("unused")
    private final LocalDate dueDate;
    public LoanChargeCommand(final Long id, final Long chargeId, final BigDecimal amount, final Integer chargeTimeType,
            final Integer chargeCalculationType, final LocalDate dueDate) {
        this.id = id;
        this.chargeId = chargeId;
        this.amount = amount;
        this.chargeTimeType = chargeTimeType;
        this.chargeCalculationType = chargeCalculationType;
        this.dueDate = dueDate;
    }
    @Override
    public int compareTo(final LoanChargeCommand o) {
        int comparison = this.chargeId.compareTo(o.chargeId);
        if (comparison == 0) {
            comparison = this.amount.compareTo(o.amount);
        }
        return comparison;
    }
}
