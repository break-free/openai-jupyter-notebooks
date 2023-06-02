
package org.apache.fineract.organisation.holiday.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.organisation.holiday.domain.HolidayStatusType;
import org.apache.fineract.organisation.holiday.domain.RescheduleType;
public final class HolidayEnumerations {
    private HolidayEnumerations() {
    }
    public static EnumOptionData holidayStatusType(final int id) {
        return holidayStatusType(HolidayStatusType.fromInt(id));
    }
    public static EnumOptionData holidayStatusType(final HolidayStatusType type) {
        EnumOptionData optionData = null;
        switch (type) {
            case INVALID:
                optionData = new EnumOptionData(HolidayStatusType.INVALID.getValue().longValue(), HolidayStatusType.INVALID.getCode(),
                        "Invalid");
            break;
            case PENDING_FOR_ACTIVATION:
                optionData = new EnumOptionData(HolidayStatusType.PENDING_FOR_ACTIVATION.getValue().longValue(),
                        HolidayStatusType.PENDING_FOR_ACTIVATION.getCode(), "Pending for activation");
            break;
            case ACTIVE:
                optionData = new EnumOptionData(HolidayStatusType.ACTIVE.getValue().longValue(), HolidayStatusType.ACTIVE.getCode(),
                        "Active");
            break;
            case DELETED:
                optionData = new EnumOptionData(HolidayStatusType.DELETED.getValue().longValue(), HolidayStatusType.DELETED.getCode(),
                        "Deleted");
            break;
        }
        return optionData;
    }
    public static EnumOptionData rescheduleType(final int id) {
        return rescheduleType(RescheduleType.fromInt(id));
    }
    public static EnumOptionData rescheduleType(final RescheduleType type) {
        EnumOptionData optionData = null;
        switch (type) {
            case RESCHEDULETONEXTREPAYMENTDATE:
                optionData = new EnumOptionData(RescheduleType.RESCHEDULETONEXTREPAYMENTDATE.getValue().longValue(),
                        RescheduleType.RESCHEDULETONEXTREPAYMENTDATE.getCode(), "Reschedule to next repayment date");
            break;
            case RESCHEDULETOSPECIFICDATE:
                optionData = new EnumOptionData(RescheduleType.RESCHEDULETOSPECIFICDATE.getValue().longValue(),
                        RescheduleType.RESCHEDULETOSPECIFICDATE.getCode(), "Reschedule to specified date");
            break;
            default:
                optionData = new EnumOptionData(RescheduleType.INVALID.getValue().longValue(), RescheduleType.INVALID.getCode(), "Invalid");
            break;
        }
        return optionData;
    }
}
