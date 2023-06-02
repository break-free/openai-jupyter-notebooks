
package org.apache.fineract.infrastructure.sms.domain;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class SmsMessageEnumerations {
    private SmsMessageEnumerations() {
    }
    public static EnumOptionData status(final Integer statusId) {
        return status(SmsMessageStatusType.fromInt(statusId));
    }
    public static EnumOptionData status(final SmsMessageStatusType status) {
        EnumOptionData optionData = new EnumOptionData(SmsMessageStatusType.INVALID.getValue().longValue(),
                SmsMessageStatusType.INVALID.getCode(), "Invalid");
        switch (status) {
            case INVALID:
                optionData = new EnumOptionData(SmsMessageStatusType.INVALID.getValue().longValue(), SmsMessageStatusType.INVALID.getCode(),
                        "Invalid");
            break;
            case PENDING:
                optionData = new EnumOptionData(SmsMessageStatusType.PENDING.getValue().longValue(), SmsMessageStatusType.PENDING.getCode(),
                        "Pending");
            break;
            case WAITING_FOR_DELIVERY_REPORT:
                optionData = new EnumOptionData(SmsMessageStatusType.WAITING_FOR_DELIVERY_REPORT.getValue().longValue(),
                        SmsMessageStatusType.WAITING_FOR_DELIVERY_REPORT.getCode(), "Waiting");
            break;
            case SENT:
                optionData = new EnumOptionData(SmsMessageStatusType.SENT.getValue().longValue(), SmsMessageStatusType.SENT.getCode(),
                        "Sent");
            break;
            case DELIVERED:
                optionData = new EnumOptionData(SmsMessageStatusType.DELIVERED.getValue().longValue(),
                        SmsMessageStatusType.DELIVERED.getCode(), "Delivered");
            break;
            case FAILED:
                optionData = new EnumOptionData(SmsMessageStatusType.FAILED.getValue().longValue(), SmsMessageStatusType.FAILED.getCode(),
                        "Failed");
            break;
        }
        return optionData;
    }
}
