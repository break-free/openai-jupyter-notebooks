
package org.apache.fineract.organisation.provisioning.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ProvisioningCategoryWritePlatformService {
    CommandProcessingResult createProvisioningCateogry(JsonCommand command);
    CommandProcessingResult updateProvisioningCategory(Long categoryId, JsonCommand command);
    CommandProcessingResult deleteProvisioningCateogry(JsonCommand command);
}
