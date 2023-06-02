
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface RecurringDepositProductWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long productId, JsonCommand command);
    CommandProcessingResult delete(Long productId);
}
