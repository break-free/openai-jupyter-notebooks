
package org.apache.fineract.portfolio.collateralmanagement.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CollateralManagementWritePlatformService {
    CommandProcessingResult createCollateral(JsonCommand command);
    CommandProcessingResult updateCollateral(Long collateralId, JsonCommand command);
    CommandProcessingResult deleteCollateral(Long collateralId);
}
