
package org.apache.fineract.portfolio.common.domain;
import java.time.DayOfWeek;
public enum DayOfWeekType {
    MONDAY(DayOfWeek.MONDAY.getValue(), "weekDayType.monday"), TUESDAY(DayOfWeek.TUESDAY.getValue(), "weekDayType.tuesday"), WEDNESDAY(
            DayOfWeek.WEDNESDAY.getValue(),
            "weekDayType.wednesday"), THURSDAY(DayOfWeek.THURSDAY.getValue(), "weekDayType.thursday"), FRIDAY(DayOfWeek.FRIDAY.getValue(),
                    "weekDayType.friday"), SATURDAY(DayOfWeek.SATURDAY.getValue(), "weekDayType.saturday"), SUNDAY(
                            DayOfWeek.SUNDAY.getValue(), "weekDayType.sunday"), INVALID(0, "weekDayType.invalid");
    private final Integer value;
    private final String code;
    DayOfWeekType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static DayOfWeekType fromInt(final Integer dayOfWeek) {
        DayOfWeekType weekDayType = INVALID;
        if (dayOfWeek != null) {
            switch (dayOfWeek) {
                case 1:
                    weekDayType = MONDAY;
                break;
                case 2:
                    weekDayType = TUESDAY;
                break;
                case 3:
                    weekDayType = WEDNESDAY;
                break;
                case 4:
                    weekDayType = THURSDAY;
                break;
                case 5:
                    weekDayType = FRIDAY;
                break;
                case 6:
                    weekDayType = SATURDAY;
                break;
                case 7:
                    weekDayType = SUNDAY;
                break;
                default:
                break;
            }
        }
        return weekDayType;
    }
}
