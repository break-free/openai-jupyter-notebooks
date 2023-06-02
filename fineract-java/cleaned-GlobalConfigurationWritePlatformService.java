
package org.apache.fineract.infrastructure.configuration.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GlobalConfigurationWritePlatformService {
    CommandProcessingResult update(Long configId, JsonCommand command);
    void addSurveyConfig(String name);
}
