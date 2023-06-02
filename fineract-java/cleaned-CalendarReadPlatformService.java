
package org.apache.fineract.portfolio.calendar.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.calendar.data.CalendarData;
import org.apache.fineract.portfolio.meeting.data.MeetingData;
public interface CalendarReadPlatformService {
    CalendarData retrieveCalendar(Long calendarId, Long entityId, Integer entityTypeId);
    Collection<CalendarData> retrieveCalendarsByEntity(Long entityId, Integer entityTypeId, List<Integer> calendarTypeOptions);
    Collection<CalendarData> retrieveParentCalendarsByEntity(Long entityId, Integer entityTypeId, List<Integer> calendarTypeOptions);
    Collection<CalendarData> retrieveAllCalendars();
    CalendarData retrieveNewCalendarDetails();
    Collection<LocalDate> generateRecurringDates(CalendarData calendarData, boolean withHistory, LocalDate tillDate);
    Collection<LocalDate> generateNextTenRecurringDates(CalendarData calendarData);
    Collection<CalendarData> updateWithRecurringDates(Collection<CalendarData> calendarsData);
    CalendarData retrieveLoanCalendar(Long loanId);
    CalendarData retrieveCollctionCalendarByEntity(Long entityId, Integer entityTypeId);
    LocalDate generateNextEligibleMeetingDateForCollection(CalendarData calendarData, MeetingData lastMeetingData);
    Boolean isCalendarAssociatedWithEntity(Long entityId, Long calendarId, Long entityTypeId);
}
