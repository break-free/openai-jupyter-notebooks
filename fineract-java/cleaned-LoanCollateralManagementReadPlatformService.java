
package org.apache.fineract.portfolio.collateralmanagement.service;
import java.util.List;
import org.apache.fineract.portfolio.collateralmanagement.data.LoanCollateralResponseData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCollateralManagement;
public interface LoanCollateralManagementReadPlatformService {
    List<LoanCollateralManagement> getLoanCollaterals(Long loanId);
    LoanCollateralResponseData getLoanCollateralResponseData(Long collateralId);
    List<LoanCollateralResponseData> getLoanCollateralResponseDataList(Long loanId);
}
