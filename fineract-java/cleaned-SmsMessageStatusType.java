
package org.apache.fineract.infrastructure.sms.domain;
public enum SmsMessageStatusType {
    INVALID(0, "smsMessageStatusType.invalid"), 
    PENDING(100, "smsMessageStatusType.pending"), 
    WAITING_FOR_DELIVERY_REPORT(150, "smsMessageStatusType.waitingForDeliveryReport"), SENT(200, "smsMessageStatusType.sent"), 
    DELIVERED(300, "smsMessageStatusType.delivered"), 
    FAILED(400, "smsMessageStatusType.failed");
    private final Integer value;
    private final String code;
    public static SmsMessageStatusType fromInt(final Integer statusValue) {
        SmsMessageStatusType enumeration = SmsMessageStatusType.INVALID;
        switch (statusValue) {
            case 100:
                enumeration = SmsMessageStatusType.PENDING;
            break;
            case 150:
                enumeration = SmsMessageStatusType.WAITING_FOR_DELIVERY_REPORT;
            break;
            case 200:
                enumeration = SmsMessageStatusType.SENT;
            break;
            case 300:
                enumeration = SmsMessageStatusType.DELIVERED;
            break;
            case 400:
                enumeration = SmsMessageStatusType.FAILED;
            break;
        }
        return enumeration;
    }
    SmsMessageStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
}
