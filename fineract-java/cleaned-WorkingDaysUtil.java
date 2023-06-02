
package org.apache.fineract.organisation.workingdays.service;
import java.time.LocalDate;
import org.apache.fineract.organisation.workingdays.data.AdjustedDateDetailsDTO;
import org.apache.fineract.organisation.workingdays.domain.RepaymentRescheduleType;
import org.apache.fineract.organisation.workingdays.domain.WorkingDays;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
public final class WorkingDaysUtil {
    private WorkingDaysUtil() {
    }
    public static LocalDate getOffSetDateIfNonWorkingDay(final LocalDate date, final LocalDate nextMeetingDate,
            final WorkingDays workingDays) {
        if (isWorkingDay(workingDays, date)) {
            return date;
        }
        final RepaymentRescheduleType rescheduleType = RepaymentRescheduleType.fromInt(workingDays.getRepaymentReschedulingType());
        switch (rescheduleType) {
            case INVALID:
                return date;
            case SAME_DAY:
                return date;
            case MOVE_TO_NEXT_WORKING_DAY:
                return getOffSetDateIfNonWorkingDay(date.plusDays(1), nextMeetingDate, workingDays);
            case MOVE_TO_NEXT_REPAYMENT_MEETING_DAY:
                return nextMeetingDate;
            case MOVE_TO_PREVIOUS_WORKING_DAY:
                return getOffSetDateIfNonWorkingDay(date.minusDays(1), nextMeetingDate, workingDays);
            default:
                return date;
        }
    }
    public static boolean isWorkingDay(final WorkingDays workingDays, final LocalDate date) {
        return CalendarUtils.isValidRedurringDate(workingDays.getRecurrence(), date, date);
    }
    public static boolean isNonWorkingDay(final WorkingDays workingDays, final LocalDate date) {
        return !isWorkingDay(workingDays, date);
    }
    public static void updateWorkingDayIfRepaymentDateIsNonWorkingDay(final AdjustedDateDetailsDTO adjustedDateDetailsDTO,
            final WorkingDays workingDays) {
        final LocalDate changedScheduleDate = getOffSetDateIfNonWorkingDay(adjustedDateDetailsDTO.getChangedScheduleDate(),
                adjustedDateDetailsDTO.getNextRepaymentPeriodDueDate(), workingDays);
        adjustedDateDetailsDTO.setChangedScheduleDate(changedScheduleDate);
    }
    public static RepaymentRescheduleType getRepaymentRescheduleType(final WorkingDays workingDays, final LocalDate date) {
        RepaymentRescheduleType rescheduleType = RepaymentRescheduleType.fromInt(workingDays.getRepaymentReschedulingType());
        return rescheduleType;
    }
}
