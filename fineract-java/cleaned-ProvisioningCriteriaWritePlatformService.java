
package org.apache.fineract.organisation.provisioning.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ProvisioningCriteriaWritePlatformService {
    CommandProcessingResult createProvisioningCriteria(JsonCommand command);
    CommandProcessingResult updateProvisioningCriteria(Long categoryId, JsonCommand command);
    CommandProcessingResult deleteProvisioningCriteria(Long entryId);
}
