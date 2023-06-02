
package org.apache.fineract.portfolio.calendar.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface CalendarDropdownReadPlatformService {
    List<EnumOptionData> retrieveCalendarEntityTypeOptions();
    List<EnumOptionData> retrieveCalendarTypeOptions();
    List<EnumOptionData> retrieveCalendarRemindByOptions();
    List<EnumOptionData> retrieveCalendarFrequencyTypeOptions();
    List<EnumOptionData> retrieveCalendarWeekDaysTypeOptions();
    List<EnumOptionData> retrieveCalendarFrequencyNthDayTypeOptions();
}
