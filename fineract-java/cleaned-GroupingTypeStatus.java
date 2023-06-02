
package org.apache.fineract.portfolio.group.domain;
public enum GroupingTypeStatus {
    INVALID(0, "groupingStatusType.invalid"), 
    PENDING(100, "groupingStatusType.pending"), 
    ACTIVE(300, "groupingStatusType.active"), 
    TRANSFER_IN_PROGRESS(303, "clientStatusType.transfer.in.progress"), 
    TRANSFER_ON_HOLD(304, "clientStatusType.transfer.on.hold"), 
    CLOSED(600, "groupingStatusType.closed");
    private final Integer value;
    private final String code;
    public static GroupingTypeStatus fromInt(final Integer statusValue) {
        GroupingTypeStatus enumeration = GroupingTypeStatus.INVALID;
        switch (statusValue) {
            case 100:
                enumeration = GroupingTypeStatus.PENDING;
            break;
            case 300:
                enumeration = GroupingTypeStatus.ACTIVE;
            break;
            case 303:
                enumeration = GroupingTypeStatus.TRANSFER_IN_PROGRESS;
            break;
            case 304:
                enumeration = GroupingTypeStatus.TRANSFER_ON_HOLD;
            break;
            case 600:
                enumeration = GroupingTypeStatus.CLOSED;
            break;
        }
        return enumeration;
    }
    GroupingTypeStatus(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public boolean hasStateOf(final GroupingTypeStatus state) {
        return this.value.equals(state.getValue());
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isPending() {
        return this.value.equals(GroupingTypeStatus.PENDING.getValue());
    }
    public boolean isActive() {
        return this.value.equals(GroupingTypeStatus.ACTIVE.getValue());
    }
    public boolean isClosed() {
        return this.value.equals(GroupingTypeStatus.CLOSED.getValue());
    }
    public boolean isTransferInProgress() {
        return this.value.equals(GroupingTypeStatus.TRANSFER_IN_PROGRESS.getValue());
    }
    public boolean isTransferOnHold() {
        return this.value.equals(GroupingTypeStatus.TRANSFER_ON_HOLD.getValue());
    }
    public boolean isUnderTransfer() {
        return isTransferInProgress() || isTransferOnHold();
    }
}
