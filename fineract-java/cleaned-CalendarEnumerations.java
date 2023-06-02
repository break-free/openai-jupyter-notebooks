
package org.apache.fineract.portfolio.calendar.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.calendar.domain.CalendarEntityType;
import org.apache.fineract.portfolio.calendar.domain.CalendarFrequencyType;
import org.apache.fineract.portfolio.calendar.domain.CalendarRemindBy;
import org.apache.fineract.portfolio.calendar.domain.CalendarType;
import org.apache.fineract.portfolio.calendar.domain.CalendarWeekDaysType;
import org.apache.fineract.portfolio.common.domain.NthDayType;
public final class CalendarEnumerations {
    private CalendarEnumerations() {
    }
    public static EnumOptionData calendarEntityType(final int id) {
        return calendarEntityType(CalendarEntityType.fromInt(id));
    }
    public static EnumOptionData calendarEntityType(final CalendarEntityType entityType) {
        final EnumOptionData optionData = new EnumOptionData(entityType.getValue().longValue(), entityType.getCode(),
                entityType.toString());
        return optionData;
    }
    public static List<EnumOptionData> calendarEntityType(final CalendarEntityType[] entityTypes) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarEntityType entityType : entityTypes) {
            optionDatas.add(calendarEntityType(entityType));
        }
        return optionDatas;
    }
    public static EnumOptionData calendarType(final int id) {
        return calendarType(CalendarType.fromInt(id));
    }
    public static EnumOptionData calendarType(final CalendarType type) {
        final EnumOptionData optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), type.toString());
        return optionData;
    }
    public static List<EnumOptionData> calendarType(final CalendarType[] types) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarType type : types) {
            optionDatas.add(calendarType(type));
        }
        return optionDatas;
    }
    public static EnumOptionData calendarRemindBy(final int id) {
        return calendarRemindBy(CalendarRemindBy.fromInt(id));
    }
    public static EnumOptionData calendarRemindBy(final CalendarRemindBy remindBy) {
        final EnumOptionData optionData = new EnumOptionData(remindBy.getValue().longValue(), remindBy.getCode(), remindBy.toString());
        return optionData;
    }
    public static List<EnumOptionData> calendarRemindBy(final CalendarRemindBy[] remindBys) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarRemindBy remindBy : remindBys) {
            optionDatas.add(calendarRemindBy(remindBy));
        }
        return optionDatas;
    }
    public static EnumOptionData calendarFrequencyType(final int id) {
        return calendarFrequencyType(CalendarFrequencyType.fromInt(id));
    }
    public static EnumOptionData calendarFrequencyType(final CalendarFrequencyType calendarFrequencyType) {
        EnumOptionData optionData = null;
        if (!calendarFrequencyType.isInvalid()) {
            optionData = new EnumOptionData(calendarFrequencyType.getValue().longValue(), calendarFrequencyType.getCode(),
                    calendarFrequencyType.toString());
        }
        return optionData;
    }
    public static List<EnumOptionData> calendarFrequencyType(final CalendarFrequencyType[] calendarFrequencyTypes) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarFrequencyType calendarFrequencyType : calendarFrequencyTypes) {
            if (!calendarFrequencyType.isInvalid()) {
                optionDatas.add(calendarFrequencyType(calendarFrequencyType));
            }
        }
        return optionDatas;
    }
    public static EnumOptionData calendarWeekDaysType(final int id) {
        return calendarWeekDaysType(CalendarWeekDaysType.fromInt(id));
    }
    public static EnumOptionData calendarWeekDaysType(final CalendarWeekDaysType calendarWeekDaysType) {
        EnumOptionData optionData = null;
        if (!calendarWeekDaysType.isInvalid()) {
            optionData = new EnumOptionData(calendarWeekDaysType.getValue().longValue(), calendarWeekDaysType.getCode(),
                    calendarWeekDaysType.toString());
        }
        return optionData;
    }
    public static List<EnumOptionData> calendarWeekDaysType(final CalendarWeekDaysType[] calendarWeekDaysTypes) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarWeekDaysType calendarWeekDaysType : calendarWeekDaysTypes) {
            if (!calendarWeekDaysType.isInvalid()) {
                optionDatas.add(calendarWeekDaysType(calendarWeekDaysType));
            }
        }
        return optionDatas;
    }
    public static EnumOptionData calendarFrequencyNthDayType(final int id) {
        return calendarFrequencyNthDayType(NthDayType.fromInt(id));
    }
    public static EnumOptionData calendarFrequencyNthDayType(final NthDayType calendarFrequencyNthDayType) {
        final EnumOptionData optionData = new EnumOptionData(calendarFrequencyNthDayType.getValue().longValue(),
                calendarFrequencyNthDayType.getCode(), calendarFrequencyNthDayType.toString());
        return optionData;
    }
    public static List<EnumOptionData> calendarFrequencyNthDayType() {
        final List<EnumOptionData> optionDatas = Arrays.asList(calendarFrequencyNthDayType(NthDayType.ONE),
                calendarFrequencyNthDayType(NthDayType.TWO), calendarFrequencyNthDayType(NthDayType.THREE),
                calendarFrequencyNthDayType(NthDayType.FOUR), calendarFrequencyNthDayType(NthDayType.LAST),
                calendarFrequencyNthDayType(NthDayType.ONDAY));
        return optionDatas;
    }
}
