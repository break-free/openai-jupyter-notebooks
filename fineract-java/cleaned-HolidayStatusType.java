
package org.apache.fineract.organisation.holiday.domain;
public enum HolidayStatusType {
    INVALID(0, "holidayStatusType.invalid"), 
    PENDING_FOR_ACTIVATION(100, "holidayStatusType.pending.for.activation"), 
    ACTIVE(300, "holidayStatusType.active"), 
    DELETED(600, "savingsAccountStatusType.transfer.in.progress");
    private final Integer value;
    private final String code;
    public static HolidayStatusType fromInt(final Integer type) {
        HolidayStatusType enumeration = HolidayStatusType.INVALID;
        switch (type) {
            case 100:
                enumeration = HolidayStatusType.PENDING_FOR_ACTIVATION;
            break;
            case 300:
                enumeration = HolidayStatusType.ACTIVE;
            break;
            case 600:
                enumeration = HolidayStatusType.DELETED;
            break;
        }
        return enumeration;
    }
    HolidayStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public boolean hasStateOf(final HolidayStatusType state) {
        return this.value.equals(state.getValue());
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isPendingActivation() {
        return this.value.equals(HolidayStatusType.PENDING_FOR_ACTIVATION.getValue());
    }
    public boolean isActive() {
        return this.value.equals(HolidayStatusType.ACTIVE.getValue());
    }
    public boolean isDeleted() {
        return this.value.equals(HolidayStatusType.DELETED.getValue());
    }
}
