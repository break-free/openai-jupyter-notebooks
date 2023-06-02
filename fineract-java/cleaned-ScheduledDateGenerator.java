
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.time.LocalDate;
import org.apache.fineract.organisation.workingdays.data.AdjustedDateDetailsDTO;
import org.apache.fineract.portfolio.calendar.domain.Calendar;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.apache.fineract.portfolio.loanaccount.data.HolidayDetailDTO;
public interface ScheduledDateGenerator {
    LocalDate getLastRepaymentDate(LoanApplicationTerms loanApplicationTerms, HolidayDetailDTO holidayDetailDTO);
    LocalDate idealDisbursementDateBasedOnFirstRepaymentDate(PeriodFrequencyType repaymentPeriodFrequencyType, int repaidEvery,
            LocalDate firstRepaymentDate, Calendar loanCalendar, HolidayDetailDTO holidayDetailDTO,
            LoanApplicationTerms loanApplicationTerms);
    LocalDate generateNextRepaymentDate(LocalDate lastRepaymentDate, LoanApplicationTerms loanApplicationTerms, boolean isFirstRepayment);
    AdjustedDateDetailsDTO adjustRepaymentDate(LocalDate dueRepaymentPeriodDate, LoanApplicationTerms loanApplicationTerms,
            HolidayDetailDTO holidayDetailDTO);
    LocalDate getRepaymentPeriodDate(PeriodFrequencyType frequency, int repaidEvery, LocalDate startDate);
    Boolean isDateFallsInSchedule(PeriodFrequencyType frequency, int repaidEvery, LocalDate startDate, LocalDate date);
    LocalDate generateNextScheduleDateStartingFromDisburseDate(LocalDate lastRepaymentDate, LoanApplicationTerms loanApplicationTerms,
            HolidayDetailDTO holidayDetailDTO);
    LocalDate generateNextScheduleDateStartingFromDisburseDateOrRescheduleDate(LocalDate lastRepaymentDate,
            LoanApplicationTerms loanApplicationTerms, HolidayDetailDTO holidayDetailDTO);
}
