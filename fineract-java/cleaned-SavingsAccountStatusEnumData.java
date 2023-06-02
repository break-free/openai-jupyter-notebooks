
package org.apache.fineract.portfolio.savings.data;
import java.io.Serializable;
public class SavingsAccountStatusEnumData implements Serializable {
    private final Long id;
    @SuppressWarnings("unused")
    private final String code;
    @SuppressWarnings("unused")
    private final String value;
    @SuppressWarnings("unused")
    private final boolean submittedAndPendingApproval;
    @SuppressWarnings("unused")
    private final boolean approved;
    @SuppressWarnings("unused")
    private final boolean rejected;
    @SuppressWarnings("unused")
    private final boolean withdrawnByApplicant;
    @SuppressWarnings("unused")
    private final boolean active;
    @SuppressWarnings("unused")
    private final boolean closed;
    @SuppressWarnings("unused")
    private final boolean prematureClosed;
    @SuppressWarnings("unused")
    private final boolean transferInProgress;
    @SuppressWarnings("unused")
    private final boolean transferOnHold;
    @SuppressWarnings("unused")
    private final boolean matured;
    public SavingsAccountStatusEnumData(final Long id, final String code, final String value, final boolean submittedAndPendingApproval,
            final boolean approved, final boolean rejected, final boolean withdrawnByApplicant, final boolean active, final boolean closed,
            final boolean prematureClosed, final boolean transferInProgress, final boolean transferOnHold, final boolean matured) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.submittedAndPendingApproval = submittedAndPendingApproval;
        this.approved = approved;
        this.rejected = rejected;
        this.withdrawnByApplicant = withdrawnByApplicant;
        this.active = active;
        this.closed = closed;
        this.prematureClosed = prematureClosed;
        this.transferInProgress = transferInProgress;
        this.transferOnHold = transferOnHold;
        this.matured = matured;
    }
    public Long id() {
        return this.id;
    }
}
