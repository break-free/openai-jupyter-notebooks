
package org.apache.fineract.useradministration.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface AppUserWritePlatformService {
    CommandProcessingResult createUser(JsonCommand command);
    CommandProcessingResult updateUser(Long userId, JsonCommand command);
    CommandProcessingResult deleteUser(Long userId);
}
