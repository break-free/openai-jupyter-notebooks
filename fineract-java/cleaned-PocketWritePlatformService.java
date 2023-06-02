
package org.apache.fineract.portfolio.self.pockets.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface PocketWritePlatformService {
    CommandProcessingResult linkAccounts(JsonCommand command);
    CommandProcessingResult delinkAccounts(JsonCommand command);
}
