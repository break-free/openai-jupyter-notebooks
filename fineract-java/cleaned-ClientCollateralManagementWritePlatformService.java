
package org.apache.fineract.portfolio.collateralmanagement.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ClientCollateralManagementWritePlatformService {
    CommandProcessingResult addClientCollateralProduct(JsonCommand command);
    CommandProcessingResult updateClientCollateralProduct(JsonCommand command);
    CommandProcessingResult deleteClientCollateralProduct(Long collateralId);
}
