
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.apache.fineract.organisation.holiday.domain.Holiday;
import org.apache.fineract.organisation.holiday.service.HolidayUtil;
import org.apache.fineract.organisation.workingdays.data.AdjustedDateDetailsDTO;
import org.apache.fineract.organisation.workingdays.service.WorkingDaysUtil;
import org.apache.fineract.portfolio.calendar.data.CalendarHistoryDataWrapper;
import org.apache.fineract.portfolio.calendar.domain.Calendar;
import org.apache.fineract.portfolio.calendar.domain.CalendarHistory;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.apache.fineract.portfolio.loanaccount.data.HolidayDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class DefaultScheduledDateGenerator implements ScheduledDateGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultScheduledDateGenerator.class);
    @Override
    public LocalDate getLastRepaymentDate(final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO) {
        final int numberOfRepayments = loanApplicationTerms.getNumberOfRepayments();
        LocalDate lastRepaymentDate = loanApplicationTerms.getExpectedDisbursementDate();
        boolean isFirstRepayment = true;
        for (int repaymentPeriod = 1; repaymentPeriod <= numberOfRepayments; repaymentPeriod++) {
            lastRepaymentDate = generateNextRepaymentDate(lastRepaymentDate, loanApplicationTerms, isFirstRepayment);
            isFirstRepayment = false;
        }
        lastRepaymentDate = adjustRepaymentDate(lastRepaymentDate, loanApplicationTerms, holidayDetailDTO).getChangedScheduleDate();
        return lastRepaymentDate;
    }
    @Override
    public LocalDate generateNextRepaymentDate(final LocalDate lastRepaymentDate, final LoanApplicationTerms loanApplicationTerms,
            boolean isFirstRepayment) {
        final LocalDate firstRepaymentPeriodDate = loanApplicationTerms.getCalculatedRepaymentsStartingFromLocalDate();
        LocalDate dueRepaymentPeriodDate = null;
        if (isFirstRepayment && firstRepaymentPeriodDate != null) {
            dueRepaymentPeriodDate = firstRepaymentPeriodDate;
        } else {
            LocalDate seedDate = null;
            String reccuringString = null;
            Calendar currentCalendar = loanApplicationTerms.getLoanCalendar();
            dueRepaymentPeriodDate = getRepaymentPeriodDate(loanApplicationTerms.getRepaymentPeriodFrequencyType(),
                    loanApplicationTerms.getRepaymentEvery(), lastRepaymentDate);
            dueRepaymentPeriodDate = (LocalDate) CalendarUtils.adjustDate(dueRepaymentPeriodDate, loanApplicationTerms.getSeedDate(),
                    loanApplicationTerms.getRepaymentPeriodFrequencyType());
            if (currentCalendar != null) {
                CalendarHistory calendarHistory = null;
                CalendarHistoryDataWrapper calendarHistoryDataWrapper = loanApplicationTerms.getCalendarHistoryDataWrapper();
                if (calendarHistoryDataWrapper != null) {
                    calendarHistory = loanApplicationTerms.getCalendarHistoryDataWrapper().getCalendarHistory(dueRepaymentPeriodDate);
                }
                if (calendarHistory == null) {
                    seedDate = currentCalendar.getStartDateLocalDate();
                    reccuringString = currentCalendar.getRecurrence();
                } else {
                    seedDate = calendarHistory.getStartDateLocalDate();
                    reccuringString = calendarHistory.getRecurrence();
                }
                dueRepaymentPeriodDate = CalendarUtils.getNextRepaymentMeetingDate(reccuringString, seedDate, lastRepaymentDate,
                        loanApplicationTerms.getRepaymentEvery(),
                        CalendarUtils.getMeetingFrequencyFromPeriodFrequencyType(loanApplicationTerms.getLoanTermPeriodFrequencyType()),
                        loanApplicationTerms.isSkipRepaymentOnFirstDayofMonth(), loanApplicationTerms.getNumberOfdays());
            }
        }
        return dueRepaymentPeriodDate;
    }
    @Override
    public AdjustedDateDetailsDTO adjustRepaymentDate(final LocalDate dueRepaymentPeriodDate,
            final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO) {
        final LocalDate adjustedDate = dueRepaymentPeriodDate;
        return getAdjustedDateDetailsDTO(dueRepaymentPeriodDate, loanApplicationTerms, holidayDetailDTO, adjustedDate);
    }
    private AdjustedDateDetailsDTO getAdjustedDateDetailsDTO(final LocalDate dueRepaymentPeriodDate,
            final LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO, final LocalDate adjustedDate) {
        final boolean isFirstRepayment = false;
        final LocalDate nextRepaymentPeriodDueDate = generateNextRepaymentDate(adjustedDate, loanApplicationTerms, isFirstRepayment);
        final AdjustedDateDetailsDTO newAdjustedDateDetailsDTO = new AdjustedDateDetailsDTO(adjustedDate, dueRepaymentPeriodDate,
                nextRepaymentPeriodDueDate);
        return recursivelyCheckNonWorkingDaysAndHolidaysAndWorkingDaysExemptionToGenerateNextRepaymentPeriodDate(newAdjustedDateDetailsDTO,
                loanApplicationTerms, holidayDetailDTO, isFirstRepayment);
    }
    private AdjustedDateDetailsDTO recursivelyCheckNonWorkingDaysAndHolidaysAndWorkingDaysExemptionToGenerateNextRepaymentPeriodDate(
            final AdjustedDateDetailsDTO adjustedDateDetailsDTO, final LoanApplicationTerms loanApplicationTerms,
            final HolidayDetailDTO holidayDetailDTO, final boolean isFirstRepayment) {
        checkAndUpdateWorkingDayIfRepaymentDateIsNonWorkingDay(adjustedDateDetailsDTO, holidayDetailDTO, loanApplicationTerms,
                isFirstRepayment);
        checkAndUpdateWorkingDayIfRepaymentDateIsHolidayDay(adjustedDateDetailsDTO, holidayDetailDTO, loanApplicationTerms,
                isFirstRepayment);
        if ((holidayDetailDTO.isHolidayEnabled() && HolidayUtil.getApplicableHoliday(adjustedDateDetailsDTO.getChangedScheduleDate(),
                holidayDetailDTO.getHolidays()) != null)
                || WorkingDaysUtil.isNonWorkingDay(holidayDetailDTO.getWorkingDays(), adjustedDateDetailsDTO.getChangedScheduleDate())) {
            recursivelyCheckNonWorkingDaysAndHolidaysAndWorkingDaysExemptionToGenerateNextRepaymentPeriodDate(adjustedDateDetailsDTO,
                    loanApplicationTerms, holidayDetailDTO, isFirstRepayment);
        }
        return adjustedDateDetailsDTO;
    }
    private void checkAndUpdateWorkingDayIfRepaymentDateIsHolidayDay(final AdjustedDateDetailsDTO adjustedDateDetailsDTO,
            final HolidayDetailDTO holidayDetailDTO, final LoanApplicationTerms loanApplicationTerms, final boolean isFirstRepayment) {
        if (holidayDetailDTO.isHolidayEnabled()) {
            Holiday applicableHolidayForNewAdjustedDate = null;
            while ((applicableHolidayForNewAdjustedDate = HolidayUtil.getApplicableHoliday(adjustedDateDetailsDTO.getChangedScheduleDate(),
                    holidayDetailDTO.getHolidays())) != null) {
                if (applicableHolidayForNewAdjustedDate.getReScheduleType().isResheduleToNextRepaymentDate()) {
                    LocalDate nextRepaymentPeriodDueDate = adjustedDateDetailsDTO.getChangedActualRepaymentDate();
                    while (!nextRepaymentPeriodDueDate.isAfter(adjustedDateDetailsDTO.getChangedScheduleDate())) {
                        nextRepaymentPeriodDueDate = generateNextRepaymentDate(nextRepaymentPeriodDueDate, loanApplicationTerms,
                                isFirstRepayment);
                    }
                    adjustedDateDetailsDTO.setChangedScheduleDate(nextRepaymentPeriodDueDate);
                    adjustedDateDetailsDTO.setNextRepaymentPeriodDueDate(nextRepaymentPeriodDueDate);
                    adjustedDateDetailsDTO.setChangedActualRepaymentDate(adjustedDateDetailsDTO.getChangedScheduleDate());
                } else {
                    HolidayUtil.updateRepaymentRescheduleDateToWorkingDayIfItIsHoliday(adjustedDateDetailsDTO,
                            applicableHolidayForNewAdjustedDate);
                }
            }
        }
    }
    private void checkAndUpdateWorkingDayIfRepaymentDateIsNonWorkingDay(final AdjustedDateDetailsDTO adjustedDateDetailsDTO,
            final HolidayDetailDTO holidayDetailDTO, final LoanApplicationTerms loanApplicationTerms, final boolean isFirstRepayment) {
        while (WorkingDaysUtil.isNonWorkingDay(holidayDetailDTO.getWorkingDays(), adjustedDateDetailsDTO.getChangedScheduleDate())) {
            if (WorkingDaysUtil
                    .getRepaymentRescheduleType(holidayDetailDTO.getWorkingDays(), adjustedDateDetailsDTO.getChangedScheduleDate())
                    .isMoveToNextRepaymentDay()) {
                while (WorkingDaysUtil.isNonWorkingDay(holidayDetailDTO.getWorkingDays(),
                        adjustedDateDetailsDTO.getNextRepaymentPeriodDueDate())
                        || adjustedDateDetailsDTO.getChangedScheduleDate()
                                .isAfter(adjustedDateDetailsDTO.getNextRepaymentPeriodDueDate())) {
                    final LocalDate nextRepaymentPeriodDueDate = generateNextRepaymentDate(
                            adjustedDateDetailsDTO.getNextRepaymentPeriodDueDate(), loanApplicationTerms, isFirstRepayment);
                    adjustedDateDetailsDTO.setNextRepaymentPeriodDueDate(nextRepaymentPeriodDueDate);
                }
            }
            WorkingDaysUtil.updateWorkingDayIfRepaymentDateIsNonWorkingDay(adjustedDateDetailsDTO, holidayDetailDTO.getWorkingDays());
        }
    }
    @Override
    public LocalDate getRepaymentPeriodDate(final PeriodFrequencyType frequency, final int repaidEvery, final LocalDate startDate) {
        LocalDate dueRepaymentPeriodDate = startDate;
        switch (frequency) {
            case DAYS:
                dueRepaymentPeriodDate = startDate.plusDays(repaidEvery);
            break;
            case WEEKS:
                dueRepaymentPeriodDate = startDate.plusWeeks(repaidEvery);
            break;
            case MONTHS:
                dueRepaymentPeriodDate = startDate.plusMonths(repaidEvery);
            break;
            case YEARS:
                dueRepaymentPeriodDate = startDate.plusYears(repaidEvery);
            break;
            case INVALID:
            break;
            case WHOLE_TERM:
                LOG.error("TODO Implement getRepaymentPeriodDate for WHOLE_TERM");
            break;
        }
        return dueRepaymentPeriodDate;
    }
    @Override
    public Boolean isDateFallsInSchedule(final PeriodFrequencyType frequency, final int repaidEvery, final LocalDate startDate,
            final LocalDate date) {
        boolean isScheduledDate = false;
        switch (frequency) {
            case DAYS:
                int diff = Math.toIntExact(ChronoUnit.DAYS.between(startDate, date));
                isScheduledDate = (diff % repaidEvery) == 0;
            break;
            case WEEKS:
                int weekDiff = Math.toIntExact(ChronoUnit.WEEKS.between(startDate, date));
                isScheduledDate = (weekDiff % repaidEvery) == 0;
                if (isScheduledDate) {
                    LocalDate modifiedDate = startDate.plusWeeks(weekDiff);
                    isScheduledDate = modifiedDate.isEqual(date);
                }
            break;
            case MONTHS:
                int monthDiff = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, date));
                isScheduledDate = (monthDiff % repaidEvery) == 0;
                if (isScheduledDate) {
                    LocalDate modifiedDate = startDate.plusMonths(monthDiff);
                    isScheduledDate = modifiedDate.isEqual(date);
                }
            break;
            case YEARS:
                int yearDiff = Math.toIntExact(ChronoUnit.YEARS.between(startDate, date));
                isScheduledDate = (yearDiff % repaidEvery) == 0;
                if (isScheduledDate) {
                    LocalDate modifiedDate = startDate.plusYears(yearDiff);
                    isScheduledDate = modifiedDate.isEqual(date);
                }
            break;
            case INVALID:
            break;
            case WHOLE_TERM:
                LOG.error("TODO Implement isDateFallsInSchedule for WHOLE_TERM");
            break;
        }
        return isScheduledDate;
    }
    @Override
    public LocalDate idealDisbursementDateBasedOnFirstRepaymentDate(final PeriodFrequencyType repaymentPeriodFrequencyType,
            final int repaidEvery, final LocalDate firstRepaymentDate, final Calendar loanCalendar, final HolidayDetailDTO holidayDetailDTO,
            final LoanApplicationTerms loanApplicationTerms) {
        LocalDate idealDisbursementDate = null;
        switch (repaymentPeriodFrequencyType) {
            case DAYS:
                idealDisbursementDate = firstRepaymentDate.minusDays(repaidEvery);
            break;
            case WEEKS:
                idealDisbursementDate = firstRepaymentDate.minusWeeks(repaidEvery);
            break;
            case MONTHS:
                if (loanCalendar == null) {
                    idealDisbursementDate = firstRepaymentDate.minusMonths(repaidEvery);
                } else {
                    idealDisbursementDate = CalendarUtils.getNewRepaymentMeetingDate(loanCalendar.getRecurrence(),
                            firstRepaymentDate.minusMonths(repaidEvery), firstRepaymentDate.minusMonths(repaidEvery), repaidEvery,
                            CalendarUtils.getMeetingFrequencyFromPeriodFrequencyType(repaymentPeriodFrequencyType),
                            holidayDetailDTO.getWorkingDays(), loanApplicationTerms.isSkipRepaymentOnFirstDayofMonth(),
                            loanApplicationTerms.getNumberOfdays());
                }
            break;
            case YEARS:
                idealDisbursementDate = firstRepaymentDate.minusYears(repaidEvery);
            break;
            case INVALID:
            break;
            case WHOLE_TERM:
                LOG.error("TODO Implement repaymentPeriodFrequencyType for WHOLE_TERM");
            break;
        }
        return idealDisbursementDate;
    }
    @Override
    public LocalDate generateNextScheduleDateStartingFromDisburseDate(LocalDate lastRepaymentDate,
            LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO) {
        LocalDate generatedDate = loanApplicationTerms.getExpectedDisbursementDate();
        boolean isFirstRepayment = true;
        while (!generatedDate.isAfter(lastRepaymentDate)) {
            generatedDate = generateNextRepaymentDate(generatedDate, loanApplicationTerms, isFirstRepayment);
            isFirstRepayment = false;
        }
        generatedDate = adjustRepaymentDate(generatedDate, loanApplicationTerms, holidayDetailDTO).getChangedScheduleDate();
        return generatedDate;
    }
    @Override
    public LocalDate generateNextScheduleDateStartingFromDisburseDateOrRescheduleDate(LocalDate lastRepaymentDate,
            LoanApplicationTerms loanApplicationTerms, final HolidayDetailDTO holidayDetailDTO) {
        LocalDate generatedDate = loanApplicationTerms.getExpectedDisbursementDate();
        boolean isFirstRepayment = true;
        if (loanApplicationTerms.getNewScheduledDueDateStart() != null) {
            generatedDate = loanApplicationTerms.getNewScheduledDueDateStart();
            isFirstRepayment = false;
        }
        LocalDate adjustedDate = generatedDate;
        while (!adjustedDate.isAfter(lastRepaymentDate)) {
            generatedDate = generateNextRepaymentDate(generatedDate, loanApplicationTerms, isFirstRepayment);
            adjustedDate = adjustRepaymentDate(generatedDate, loanApplicationTerms, holidayDetailDTO).getChangedScheduleDate();
            isFirstRepayment = false;
        }
        return adjustedDate;
    }
}
