
package org.apache.fineract.portfolio.collateralmanagement.service;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateralmanagement.data.CollateralManagementData;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementDomain;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CollateralManagementReadPlatformServiceImpl implements CollateralManagementReadPlatformService {
    private final PlatformSecurityContext context;
    private final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper;
    @Autowired
    public CollateralManagementReadPlatformServiceImpl(final PlatformSecurityContext context,
            final CollateralManagementRepositoryWrapper collateralManagementRepositoryWrapper) {
        this.context = context;
        this.collateralManagementRepositoryWrapper = collateralManagementRepositoryWrapper;
    }
    @Override
    public CollateralManagementData getCollateralProduct(Long collateralId) {
        final CollateralManagementDomain collateralManagementDomain = this.collateralManagementRepositoryWrapper
                .getCollateral(collateralId);
        return CollateralManagementData.createNew(collateralManagementDomain);
    }
    @Override
    public List<CollateralManagementData> getAllCollateralProducts() {
        final List<CollateralManagementDomain> collateralManagementDomainSet = this.collateralManagementRepositoryWrapper
                .getAllCollaterals();
        List<CollateralManagementData> collateralManagementDataList = new ArrayList<>();
        for (CollateralManagementDomain collateralManagementDomain : collateralManagementDomainSet) {
            collateralManagementDataList.add(CollateralManagementData.createNew(collateralManagementDomain));
        }
        return collateralManagementDataList;
    }
}
