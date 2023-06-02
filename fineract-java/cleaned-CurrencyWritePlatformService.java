
package org.apache.fineract.organisation.monetary.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CurrencyWritePlatformService {
    CommandProcessingResult updateAllowedCurrencies(JsonCommand command);
}
