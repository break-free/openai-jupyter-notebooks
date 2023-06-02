
package org.apache.fineract.portfolio.collateralmanagement.domain;
import java.util.List;
import org.apache.fineract.portfolio.collateral.exception.CollateralNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CollateralManagementRepositoryWrapper {
    private final CollateralManagementDomainRepository collateralManagementDomainRepository;
    @Autowired
    public CollateralManagementRepositoryWrapper(final CollateralManagementDomainRepository collateralManagementDomainRepository) {
        this.collateralManagementDomainRepository = collateralManagementDomainRepository;
    }
    public CollateralManagementDomain create(CollateralManagementDomain collateralData) {
        return this.collateralManagementDomainRepository.saveAndFlush(collateralData);
    }
    public CollateralManagementDomain getCollateral(Long collateralId) {
        return this.collateralManagementDomainRepository.findById(collateralId)
                .orElseThrow(() -> new CollateralNotFoundException(collateralId));
    }
    public List<CollateralManagementDomain> getAllCollaterals() {
        return this.collateralManagementDomainRepository.findAll();
    }
    public CollateralManagementDomain update(CollateralManagementDomain collateralManagementData) {
        return this.collateralManagementDomainRepository.saveAndFlush(collateralManagementData);
    }
    public void delete(final Long collateralId) {
        this.collateralManagementDomainRepository.deleteById(collateralId);
    }
}
