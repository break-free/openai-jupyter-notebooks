
package org.apache.fineract.organisation.office.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface OfficeWritePlatformService {
    CommandProcessingResult createOffice(JsonCommand command);
    CommandProcessingResult updateOffice(Long officeId, JsonCommand command);
    CommandProcessingResult officeTransaction(JsonCommand command);
    CommandProcessingResult deleteOfficeTransaction(Long id, JsonCommand command);
}
