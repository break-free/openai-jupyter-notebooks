
package org.apache.fineract.portfolio.calendar.data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.apache.fineract.portfolio.calendar.domain.CalendarHistory;
public class CalendarHistoryDataWrapper {
    private final List<CalendarHistory> calendarHistoryList;
    public CalendarHistoryDataWrapper(final Set<CalendarHistory> calendarHistoryList) {
        this.calendarHistoryList = new ArrayList<>();
        this.calendarHistoryList.addAll(calendarHistoryList);
        final Comparator<CalendarHistory> orderByDate = new Comparator<CalendarHistory>() {
            @Override
            public int compare(CalendarHistory calendarHistory1, CalendarHistory calendarHistory2) {
                return calendarHistory1.getEndDateLocalDate().compareTo(calendarHistory2.getEndDateLocalDate());
            }
        };
        Collections.sort(this.calendarHistoryList, orderByDate);
    }
    public CalendarHistory getCalendarHistory(final LocalDate dueRepaymentPeriodDate) {
        CalendarHistory calendarHistory = null;
        for (CalendarHistory history : this.calendarHistoryList) {
            if (history.getEndDateLocalDate().isAfter(dueRepaymentPeriodDate)) {
                calendarHistory = history;
                break;
            }
        }
        return calendarHistory;
    }
    public List<CalendarHistory> getCalendarHistoryList() {
        return this.calendarHistoryList;
    }
}
