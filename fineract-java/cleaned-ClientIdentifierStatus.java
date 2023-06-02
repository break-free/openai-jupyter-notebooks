
package org.apache.fineract.portfolio.client.domain;
public enum ClientIdentifierStatus {
    INACTIVE(100, "clientIdentifierStatusType.inactive"), 
    ACTIVE(200, "clientIdentifierStatusType.active"), 
    INVALID(0, "clientIdentifierStatusType.invalid");
    private final Integer value;
    private final String code;
    public static ClientIdentifierStatus fromInt(final Integer statusValue) {
        ClientIdentifierStatus enumeration = ClientIdentifierStatus.INVALID;
        switch (statusValue) {
            case 100:
                enumeration = ClientIdentifierStatus.INACTIVE;
            break;
            case 200:
                enumeration = ClientIdentifierStatus.ACTIVE;
            break;
        }
        return enumeration;
    }
    ClientIdentifierStatus(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isInactive() {
        return this.value.equals(ClientIdentifierStatus.INACTIVE.getValue());
    }
    public boolean isActive() {
        return this.value.equals(ClientIdentifierStatus.ACTIVE.getValue());
    }
}
