
package org.apache.fineract.organisation.teller.data;
import java.io.Serializable;
import java.time.LocalDate;
public final class TellerJournalData implements Serializable {
    private final Long officeId;
    private final Long tellerId;
    private final LocalDate day;
    private final Double openingBalance;
    private final Double settledBalance;
    private final Double closingBalance;
    private final Double sumReceipts;
    private final Double sumPayments;
    private TellerJournalData(final Long officeId, final Long tellerId, final LocalDate day, final Double openingBalance,
            final Double settledBalance, final Double closingBalance, final Double sumReceipts, final Double sumPayments) {
        this.officeId = officeId;
        this.tellerId = tellerId;
        this.day = day;
        this.openingBalance = openingBalance;
        this.settledBalance = settledBalance;
        this.closingBalance = closingBalance;
        this.sumReceipts = sumReceipts;
        this.sumPayments = sumPayments;
    }
    public static TellerJournalData instance(final Long officeId, final Long tellerId, final LocalDate day, final Double openingBalance,
            final Double settledBalance, final Double closingBalance, final Double sumReceipts, final Double sumPayments) {
        return new TellerJournalData(officeId, tellerId, day, openingBalance, settledBalance, closingBalance, sumReceipts, sumPayments);
    }
    public Long getOfficeId() {
        return officeId;
    }
    public Long getTellerId() {
        return tellerId;
    }
    public LocalDate getDay() {
        return day;
    }
    public Double getOpeningBalance() {
        return openingBalance;
    }
    public Double getSettledBalance() {
        return settledBalance;
    }
    public Double getClosingBalance() {
        return closingBalance;
    }
    public Double getSumReceipts() {
        return sumReceipts;
    }
    public Double getSumPayments() {
        return sumPayments;
    }
}
