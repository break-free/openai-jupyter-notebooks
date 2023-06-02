
package org.apache.fineract.portfolio.account.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public class StandingInstructionDuesData {
    private final LocalDate dueDate;
    private final BigDecimal totalDueAmount;
    public StandingInstructionDuesData(final LocalDate dueDate, final BigDecimal totalDueAmount) {
        this.dueDate = dueDate;
        this.totalDueAmount = totalDueAmount;
    }
    public LocalDate dueDate() {
        return this.dueDate;
    }
    public BigDecimal totalDueAmount() {
        return this.totalDueAmount;
    }
}
