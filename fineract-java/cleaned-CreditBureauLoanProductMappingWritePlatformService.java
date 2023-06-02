
package org.apache.fineract.infrastructure.creditbureau.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CreditBureauLoanProductMappingWritePlatformService {
    CommandProcessingResult addCreditBureauLoanProductMapping(Long creditBureauId, JsonCommand command);
    CommandProcessingResult updateCreditBureauLoanProductMapping(JsonCommand command);
}
