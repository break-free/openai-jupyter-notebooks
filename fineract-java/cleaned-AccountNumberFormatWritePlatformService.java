
package org.apache.fineract.infrastructure.accountnumberformat.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface AccountNumberFormatWritePlatformService {
    CommandProcessingResult createAccountNumberFormat(JsonCommand command);
    CommandProcessingResult updateAccountNumberFormat(Long accountNumberFormatId, JsonCommand command);
    CommandProcessingResult deleteAccountNumberFormat(Long accountNumberFormatId);
}
