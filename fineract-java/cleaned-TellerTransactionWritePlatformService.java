
package org.apache.fineract.organisation.teller.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface TellerTransactionWritePlatformService {
    CommandProcessingResult createTellerTransaction(JsonCommand command);
}
