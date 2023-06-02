
package org.apache.fineract.portfolio.collateralmanagement.service;
import java.util.List;
import org.apache.fineract.portfolio.collateralmanagement.data.CollateralManagementData;
public interface CollateralManagementReadPlatformService {
    CollateralManagementData getCollateralProduct(Long collateralId);
    List<CollateralManagementData> getAllCollateralProducts();
}
