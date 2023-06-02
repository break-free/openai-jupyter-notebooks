
package org.apache.fineract.organisation.staff.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface StaffWritePlatformService {
    CommandProcessingResult createStaff(JsonCommand command);
    CommandProcessingResult updateStaff(Long staffId, JsonCommand command);
}
