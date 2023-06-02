
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.data;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
public class LoanRescheduleRequestStatusEnumData {
    private final Long id;
    private final String code;
    private final String value;
    private final boolean pendingApproval;
    private final boolean approved;
    private final boolean rejected;
    public LoanRescheduleRequestStatusEnumData(Long id, String code, String value) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.pendingApproval = Long.valueOf(LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getValue()).equals(this.id);
        this.approved = Long.valueOf(LoanStatus.APPROVED.getValue()).equals(this.id);
        this.rejected = Long.valueOf(LoanStatus.REJECTED.getValue()).equals(this.id);
    }
    public Long id() {
        return this.id;
    }
    public String code() {
        return this.code;
    }
    public String value() {
        return this.value;
    }
    public boolean isPendingApproval() {
        return this.pendingApproval;
    }
    public boolean isApproved() {
        return this.approved;
    }
    public boolean isRejected() {
        return this.rejected;
    }
}
