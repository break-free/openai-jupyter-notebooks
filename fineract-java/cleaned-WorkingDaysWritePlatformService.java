
package org.apache.fineract.organisation.workingdays.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface WorkingDaysWritePlatformService {
    CommandProcessingResult updateWorkingDays(JsonCommand command);
}
