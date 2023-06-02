
package org.apache.fineract.organisation.holiday.service;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.organisation.holiday.domain.Holiday;
import org.apache.fineract.organisation.workingdays.data.AdjustedDateDetailsDTO;
public final class HolidayUtil {
    private HolidayUtil() {
    }
    public static LocalDate getRepaymentRescheduleDateToIfHoliday(LocalDate repaymentDate, final List<Holiday> holidays) {
        for (final Holiday holiday : holidays) {
            if (repaymentDate.equals(holiday.getFromDateLocalDate()) || repaymentDate.equals(holiday.getToDateLocalDate())
                    || (repaymentDate.isAfter(holiday.getFromDateLocalDate()) && repaymentDate.isBefore(holiday.getToDateLocalDate()))) {
                repaymentDate = getRepaymentRescheduleDateIfHoliday(repaymentDate, holidays);
            }
        }
        return repaymentDate;
    }
    private static LocalDate getRepaymentRescheduleDateIfHoliday(final LocalDate repaymentDate, final List<Holiday> holidays) {
        for (final Holiday holiday : holidays) {
            if (repaymentDate.equals(holiday.getFromDateLocalDate()) || repaymentDate.equals(holiday.getToDateLocalDate())
                    || (repaymentDate.isAfter(holiday.getFromDateLocalDate()) && repaymentDate.isBefore(holiday.getToDateLocalDate()))) {
                return holiday.getRepaymentsRescheduledToLocalDate();
            }
        }
        return repaymentDate;
    }
    public static boolean isHoliday(final LocalDate date, final List<Holiday> holidays) {
        for (final Holiday holiday : holidays) {
            if (date.isEqual(holiday.getFromDateLocalDate()) || date.isEqual(holiday.getToDateLocalDate())
                    || (date.isAfter(holiday.getFromDateLocalDate()) && date.isBefore(holiday.getToDateLocalDate()))) {
                return true;
            }
        }
        return false;
    }
    public static Holiday getApplicableHoliday(final LocalDate repaymentDate, final List<Holiday> holidays) {
        Holiday referedHoliday = null;
        for (final Holiday holiday : holidays) {
            if (!repaymentDate.isBefore(holiday.getFromDateLocalDate()) && !repaymentDate.isAfter(holiday.getToDateLocalDate())) {
                referedHoliday = holiday;
            }
        }
        return referedHoliday;
    }
    public static void updateRepaymentRescheduleDateToWorkingDayIfItIsHoliday(final AdjustedDateDetailsDTO adjustedDateDetailsDTO,
            final Holiday holiday) {
        if (holiday.getReScheduleType().isRescheduleToSpecificDate()) {
            adjustedDateDetailsDTO.setChangedScheduleDate(holiday.getRepaymentsRescheduledToLocalDate());
        }
    }
}
