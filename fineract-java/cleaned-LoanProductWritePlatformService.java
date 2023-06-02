
package org.apache.fineract.portfolio.loanproduct.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface LoanProductWritePlatformService {
    CommandProcessingResult createLoanProduct(JsonCommand command);
    CommandProcessingResult updateLoanProduct(Long loanProductId, JsonCommand command);
}
