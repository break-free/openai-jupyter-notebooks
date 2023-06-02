
package org.apache.fineract.infrastructure.campaigns.email.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface EmailConfigurationWritePlatformService {
    CommandProcessingResult update(JsonCommand command);
}
