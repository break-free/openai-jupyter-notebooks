
package org.apache.fineract.organisation.holiday.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.organisation.holiday.data.HolidayData;
public interface HolidayReadPlatformService {
    Collection<HolidayData> retrieveAllHolidaysBySearchParamerters(Long officeId, LocalDate fromDate, LocalDate toDate);
    HolidayData retrieveHoliday(Long holidayId);
    List<EnumOptionData> retrieveRepaymentScheduleUpdationTyeOptions();
}
