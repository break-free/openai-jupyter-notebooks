
package org.apache.fineract.portfolio.tax.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface TaxWritePlatformService {
    CommandProcessingResult createTaxComponent(JsonCommand command);
    CommandProcessingResult updateTaxComponent(Long id, JsonCommand command);
    CommandProcessingResult createTaxGroup(JsonCommand command);
    CommandProcessingResult updateTaxGroup(Long id, JsonCommand command);
}
