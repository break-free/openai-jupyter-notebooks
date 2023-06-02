
package org.apache.fineract.portfolio.self.account.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface SelfBeneficiariesTPTWritePlatformService {
    CommandProcessingResult add(JsonCommand command);
    CommandProcessingResult update(JsonCommand command);
    CommandProcessingResult delete(JsonCommand command);
}
