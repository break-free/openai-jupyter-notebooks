
package org.apache.fineract.portfolio.collateralmanagement.service;
import java.util.List;
import org.apache.fineract.portfolio.collateralmanagement.data.ClientCollateralManagementData;
import org.apache.fineract.portfolio.collateralmanagement.data.LoanCollateralTemplateData;
public interface ClientCollateralManagementReadPlatformService {
    ClientCollateralManagementData getClientCollateralManagementData(Long collateralId);
    List<ClientCollateralManagementData> getClientCollaterals(Long clientId, Long prodId);
    List<LoanCollateralTemplateData> getLoanCollateralTemplate(Long clientId);
}
