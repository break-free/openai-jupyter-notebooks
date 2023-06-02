
package org.apache.fineract.organisation.holiday.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface HolidayWritePlatformService {
    CommandProcessingResult createHoliday(JsonCommand command);
    CommandProcessingResult updateHoliday(JsonCommand command);
    CommandProcessingResult activateHoliday(Long holidayId);
    CommandProcessingResult deleteHoliday(Long holidayId);
}
