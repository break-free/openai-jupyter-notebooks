
package org.apache.fineract.portfolio.collateral.service;
import java.util.List;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
public interface CollateralReadPlatformService {
    List<CollateralData> retrieveCollateralsForValidLoan(Long loanId);
    List<CollateralData> retrieveCollaterals(Long loanId);
    CollateralData retrieveCollateral(Long loanId, Long collateralId);
}
