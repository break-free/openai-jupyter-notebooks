
package org.apache.fineract.infrastructure.campaigns.email.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface EmailWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long resourceId, JsonCommand command);
    CommandProcessingResult delete(Long resourceId);
}
