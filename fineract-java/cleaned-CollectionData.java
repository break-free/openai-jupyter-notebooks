
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public final class CollectionData {
    private final BigDecimal availableDisbursementAmount;
    private final int pastDueDays;
    private final LocalDate nextPaymentDueDate;
    private final int delinquentDays;
    private final LocalDate delinquentDate;
    private final BigDecimal delinquentAmount;
    private final LocalDate lastPaymentDate;
    private final BigDecimal lastPaymentAmount;
    private CollectionData(BigDecimal availableDisbursementAmount, int pastDueDays, LocalDate nextPaymentDueDate, int delinquentDays,
            LocalDate delinquentDate, BigDecimal delinquentAmount, LocalDate lastPaymentDate, BigDecimal lastPaymentAmount) {
        this.availableDisbursementAmount = availableDisbursementAmount;
        this.pastDueDays = pastDueDays;
        this.nextPaymentDueDate = nextPaymentDueDate;
        this.delinquentDays = delinquentDays;
        this.delinquentDate = delinquentDate;
        this.delinquentAmount = delinquentAmount;
        this.lastPaymentDate = lastPaymentDate;
        this.lastPaymentAmount = lastPaymentAmount;
    }
    public static CollectionData instance(BigDecimal availableDisbursementAmount, int pastDueDays, LocalDate nextPaymentDueDate,
            int delinquentDays, LocalDate delinquentDate, BigDecimal delinquentAmount, LocalDate lastPaymentDate,
            BigDecimal lastPaymentAmount) {
        return new CollectionData(availableDisbursementAmount, pastDueDays, nextPaymentDueDate, delinquentDays, delinquentDate,
                delinquentAmount, lastPaymentDate, lastPaymentAmount);
    }
    public static CollectionData template() {
        final BigDecimal zero = BigDecimal.ZERO;
        return new CollectionData(zero, 0, null, 0, null, zero, null, zero);
    }
    public BigDecimal getAvailableDisbursementAmount() {
        return availableDisbursementAmount;
    }
    public int getPastDueDays() {
        return pastDueDays;
    }
    public LocalDate getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }
    public int getDelinquentDays() {
        return delinquentDays;
    }
    public LocalDate getDelinquentDate() {
        return delinquentDate;
    }
    public BigDecimal getDelinquentAmount() {
        return delinquentAmount;
    }
    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }
    public BigDecimal getLastPaymentAmount() {
        return lastPaymentAmount;
    }
}
