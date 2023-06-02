
package org.apache.fineract.portfolio.calendar.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.calendar.domain.CalendarEntityType;
import org.apache.fineract.portfolio.calendar.domain.CalendarFrequencyType;
import org.apache.fineract.portfolio.calendar.domain.CalendarRemindBy;
import org.apache.fineract.portfolio.calendar.domain.CalendarType;
import org.apache.fineract.portfolio.calendar.domain.CalendarWeekDaysType;
import org.springframework.stereotype.Service;
@Service
public class CalendarDropdownReadPlatformServiceImpl implements CalendarDropdownReadPlatformService {
    @Override
    public List<EnumOptionData> retrieveCalendarEntityTypeOptions() {
        return CalendarEnumerations.calendarEntityType(CalendarEntityType.values());
    }
    @Override
    public List<EnumOptionData> retrieveCalendarTypeOptions() {
        return CalendarEnumerations.calendarType(CalendarType.values());
    }
    @Override
    public List<EnumOptionData> retrieveCalendarRemindByOptions() {
        return CalendarEnumerations.calendarRemindBy(CalendarRemindBy.values());
    }
    @Override
    public List<EnumOptionData> retrieveCalendarFrequencyTypeOptions() {
        return CalendarEnumerations.calendarFrequencyType(CalendarFrequencyType.values());
    }
    @Override
    public List<EnumOptionData> retrieveCalendarWeekDaysTypeOptions() {
        return CalendarEnumerations.calendarWeekDaysType(CalendarWeekDaysType.values());
    }
    @Override
    public List<EnumOptionData> retrieveCalendarFrequencyNthDayTypeOptions() {
        return CalendarEnumerations.calendarFrequencyNthDayType();
    }
}
