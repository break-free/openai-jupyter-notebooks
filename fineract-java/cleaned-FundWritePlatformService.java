
package org.apache.fineract.portfolio.fund.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface FundWritePlatformService {
    CommandProcessingResult createFund(JsonCommand command);
    CommandProcessingResult updateFund(Long fundId, JsonCommand command);
}
