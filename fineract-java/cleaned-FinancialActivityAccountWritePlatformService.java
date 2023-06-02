
package org.apache.fineract.accounting.financialactivityaccount.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface FinancialActivityAccountWritePlatformService {
    CommandProcessingResult createFinancialActivityAccountMapping(JsonCommand command);
    CommandProcessingResult updateGLAccountActivityMapping(Long mappingId, JsonCommand command);
    CommandProcessingResult deleteGLAccountActivityMapping(Long mappingId, JsonCommand command);
}
