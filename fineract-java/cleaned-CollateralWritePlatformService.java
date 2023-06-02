
package org.apache.fineract.portfolio.collateral.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CollateralWritePlatformService {
    CommandProcessingResult addCollateral(Long loanId, JsonCommand command);
    CommandProcessingResult updateCollateral(Long loanId, Long collateralId, JsonCommand command);
    CommandProcessingResult deleteCollateral(Long loanId, Long collateralId, Long commandId);
}
