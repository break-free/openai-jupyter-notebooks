
package org.apache.fineract.organisation.teller.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface TellerWritePlatformService {
    CommandProcessingResult createTeller(JsonCommand command);
    CommandProcessingResult modifyTeller(Long tellerId, JsonCommand command);
    CommandProcessingResult deleteTeller(Long tellerId);
    CommandProcessingResult allocateCashierToTeller(Long tellerId, JsonCommand command);
    CommandProcessingResult updateCashierAllocation(Long tellerId, Long cashierId, JsonCommand command);
    CommandProcessingResult deleteCashierAllocation(Long tellerId, Long cashierId, JsonCommand command);
    CommandProcessingResult allocateCashToCashier(Long cashierId, JsonCommand command);
    CommandProcessingResult settleCashFromCashier(Long cashierId, JsonCommand command);
}
