
package org.apache.fineract.portfolio.collateralmanagement.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface LoanCollateralManagementWritePlatformService {
    CommandProcessingResult deleteLoanCollateral(JsonCommand command);
}
