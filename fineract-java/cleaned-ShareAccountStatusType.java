
package org.apache.fineract.portfolio.shareaccounts.domain;
public enum ShareAccountStatusType {
    INVALID(0, "shareAccountStatusType.invalid"), 
    SUBMITTED_AND_PENDING_APPROVAL(100, "shareAccountStatusType.submitted.and.pending.approval"), 
    APPROVED(200, "shareAccountStatusType.approved"), 
    ACTIVE(300, "shareAccountStatusType.active"), 
    REJECTED(500, "shareAccountStatusType.rejected"), 
    CLOSED(600, "shareAccountStatusType.closed");
    private final Integer value;
    private final String code;
    public static ShareAccountStatusType fromInt(final Integer type) {
        ShareAccountStatusType enumeration = ShareAccountStatusType.INVALID;
        switch (type) {
            case 100:
                enumeration = ShareAccountStatusType.SUBMITTED_AND_PENDING_APPROVAL;
            break;
            case 200:
                enumeration = ShareAccountStatusType.APPROVED;
            break;
            case 300:
                enumeration = ShareAccountStatusType.ACTIVE;
            break;
            case 500:
                enumeration = ShareAccountStatusType.REJECTED;
            break;
            case 600:
                enumeration = ShareAccountStatusType.CLOSED;
            break;
        }
        return enumeration;
    }
    ShareAccountStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public boolean hasStateOf(final ShareAccountStatusType state) {
        return this.value.equals(state.getValue());
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isSubmittedAndPendingApproval() {
        return this.value.equals(ShareAccountStatusType.SUBMITTED_AND_PENDING_APPROVAL.getValue());
    }
    public boolean isApproved() {
        return this.value.equals(ShareAccountStatusType.APPROVED.getValue());
    }
    public boolean isRejected() {
        return this.value.equals(ShareAccountStatusType.REJECTED.getValue());
    }
    public boolean isActive() {
        return this.value.equals(ShareAccountStatusType.ACTIVE.getValue());
    }
    public boolean isActiveOrAwaitingApprovalOrDisbursal() {
        return isApproved() || isSubmittedAndPendingApproval() || isActive();
    }
    public boolean isClosed() {
        return this.value.equals(ShareAccountStatusType.CLOSED.getValue()) || isRejected();
    }
}
