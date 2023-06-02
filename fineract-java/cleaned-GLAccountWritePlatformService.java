
package org.apache.fineract.accounting.glaccount.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GLAccountWritePlatformService {
    CommandProcessingResult createGLAccount(JsonCommand command);
    CommandProcessingResult updateGLAccount(Long glAccountId, JsonCommand command);
    CommandProcessingResult deleteGLAccount(Long glAccountId);
}
