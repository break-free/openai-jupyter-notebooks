
package org.apache.fineract.infrastructure.hooks.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface HookWritePlatformService {
    CommandProcessingResult createHook(JsonCommand command);
    CommandProcessingResult updateHook(Long hookId, JsonCommand command);
    CommandProcessingResult deleteHook(Long hookId);
}
