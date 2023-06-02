
package org.apache.fineract.portfolio.account.domain;
public enum StandingInstructionStatus {
    INVALID(0, "standingInstructionStatus.invalid"), 
    ACTIVE(1, "standingInstructionStatus.active"), 
    DISABLED(2, "standingInstructionStatus.disabled"), 
    DELETED(3, "standingInstructionStatus.deleted");
    private final Integer value;
    private final String code;
    public static StandingInstructionStatus fromInt(final Integer statusValue) {
        StandingInstructionStatus enumeration = StandingInstructionStatus.INVALID;
        switch (statusValue) {
            case 1:
                enumeration = StandingInstructionStatus.ACTIVE;
            break;
            case 2:
                enumeration = StandingInstructionStatus.DISABLED;
            break;
            case 3:
                enumeration = StandingInstructionStatus.DELETED;
            break;
        }
        return enumeration;
    }
    StandingInstructionStatus(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public boolean hasStateOf(final StandingInstructionStatus state) {
        return this.value.equals(state.getValue());
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isDisabled() {
        return this.value.equals(StandingInstructionStatus.DISABLED.getValue());
    }
    public boolean isDeleted() {
        return this.value.equals(StandingInstructionStatus.DELETED.getValue());
    }
    public boolean isActive() {
        return this.value.equals(StandingInstructionStatus.ACTIVE.getValue());
    }
}
