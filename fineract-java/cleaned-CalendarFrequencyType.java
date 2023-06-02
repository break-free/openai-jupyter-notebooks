
package org.apache.fineract.portfolio.calendar.domain;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.springframework.util.StringUtils;
public enum CalendarFrequencyType {
    INVALID(0, "calendarFrequencyType.invalid"), DAILY(1, "calendarFrequencyType.daily"), WEEKLY(2,
            "calendarFrequencyType.weekly"), MONTHLY(3, "calendarFrequencyType.monthly"), YEARLY(4, "calendarFrequencyType.yearly");
    private final Integer value;
    private final String code;
    CalendarFrequencyType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    private static final Map<Integer, CalendarFrequencyType> intToEnumMap = new HashMap<>();
    private static int minValue = CalendarFrequencyType.DAILY.value;
    private static int maxValue = CalendarFrequencyType.YEARLY.value;
    static {
        for (final CalendarFrequencyType type : CalendarFrequencyType.values()) {
            intToEnumMap.put(type.value, type);
        }
    }
    public static CalendarFrequencyType fromInt(final int i) {
        final CalendarFrequencyType type = intToEnumMap.get(Integer.valueOf(i));
        return type;
    }
    public static CalendarFrequencyType fromString(final String frequencyString) {
        CalendarFrequencyType frequency = CalendarFrequencyType.INVALID;
        if (!StringUtils.hasText(frequencyString)) {
            return frequency;
        }
        if (frequencyString.equalsIgnoreCase(CalendarFrequencyType.DAILY.toString())) {
            frequency = CalendarFrequencyType.DAILY;
        } else if (frequencyString.equalsIgnoreCase(CalendarFrequencyType.WEEKLY.toString())) {
            frequency = CalendarFrequencyType.WEEKLY;
        } else if (frequencyString.equalsIgnoreCase(CalendarFrequencyType.MONTHLY.toString())) {
            frequency = CalendarFrequencyType.MONTHLY;
        } else if (frequencyString.equalsIgnoreCase(CalendarFrequencyType.YEARLY.toString())) {
            frequency = CalendarFrequencyType.YEARLY;
        }
        return frequency;
    }
    public static int getMinValue() {
        return minValue;
    }
    public static int getMaxValue() {
        return maxValue;
    }
    @Override
    public String toString() {
        return name().toString();
    }
    public boolean isWeekly() {
        return this.value.equals(CalendarFrequencyType.WEEKLY.value);
    }
    public boolean isMonthly() {
        return this.value.equals(CalendarFrequencyType.MONTHLY.value);
    }
    public boolean isInvalid() {
        return this.value.equals(CalendarFrequencyType.INVALID.value);
    }
    public static CalendarFrequencyType from(final PeriodFrequencyType periodFrequencyType) {
        switch (periodFrequencyType) {
            case DAYS:
                return CalendarFrequencyType.DAILY;
            case WEEKS:
                return CalendarFrequencyType.WEEKLY;
            case MONTHS:
                return CalendarFrequencyType.MONTHLY;
            case YEARS:
                return CalendarFrequencyType.YEARLY;
            default:
                return CalendarFrequencyType.INVALID;
        }
    }
    public static PeriodFrequencyType from(final CalendarFrequencyType frequencyType) {
        switch (frequencyType) {
            case DAILY:
                return PeriodFrequencyType.DAYS;
            case WEEKLY:
                return PeriodFrequencyType.WEEKS;
            case MONTHLY:
                return PeriodFrequencyType.MONTHS;
            case YEARLY:
                return PeriodFrequencyType.YEARS;
            default:
                return PeriodFrequencyType.INVALID;
        }
    }
}
