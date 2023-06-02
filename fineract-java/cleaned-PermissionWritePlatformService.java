
package org.apache.fineract.useradministration.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface PermissionWritePlatformService {
    CommandProcessingResult updateMakerCheckerPermissions(JsonCommand command);
}
