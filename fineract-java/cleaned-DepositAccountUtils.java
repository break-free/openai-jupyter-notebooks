
package org.apache.fineract.portfolio.savings;
import java.time.LocalDate;
import org.apache.fineract.portfolio.calendar.domain.CalendarFrequencyType;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class DepositAccountUtils {
    private DepositAccountUtils() {
    }
    private static final Logger LOG = LoggerFactory.getLogger(DepositAccountUtils.class);
    public static final int GENERATE_MINIMUM_NUMBER_OF_FUTURE_INSTALMENTS = 5;
    public static LocalDate calculateNextDepositDate(final LocalDate lastDepositDate, final PeriodFrequencyType frequency,
            final int recurringEvery) {
        LocalDate nextDepositDate = lastDepositDate;
        switch (frequency) {
            case DAYS:
                nextDepositDate = lastDepositDate.plusDays(recurringEvery);
            break;
            case WEEKS:
                nextDepositDate = lastDepositDate.plusWeeks(recurringEvery);
            break;
            case MONTHS:
                nextDepositDate = lastDepositDate.plusMonths(recurringEvery);
            break;
            case YEARS:
                nextDepositDate = lastDepositDate.plusYears(recurringEvery);
            break;
            case INVALID:
            break;
            case WHOLE_TERM:
                LOG.error("TODO Implement calculateNextDepositDate for WHOLE_TERM");
            break;
        }
        return nextDepositDate;
    }
    public static LocalDate calculateNextDepositDate(final LocalDate lastDepositDate, final String recurrence) {
        final PeriodFrequencyType frequencyType = CalendarFrequencyType.from(CalendarUtils.getFrequency(recurrence));
        Integer frequency = CalendarUtils.getInterval(recurrence);
        frequency = frequency == -1 ? 1 : frequency;
        return calculateNextDepositDate(lastDepositDate, frequencyType, frequency);
    }
}
