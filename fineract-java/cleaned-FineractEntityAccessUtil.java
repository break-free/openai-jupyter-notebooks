
package org.apache.fineract.infrastructure.entityaccess.service;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.configuration.domain.GlobalConfigurationProperty;
import org.apache.fineract.infrastructure.configuration.domain.GlobalConfigurationRepositoryWrapper;
import org.apache.fineract.infrastructure.entityaccess.FineractEntityAccessConstants;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityAccessType;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityRelation;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityRelationRepositoryWrapper;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityToEntityMapping;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityToEntityMappingRepository;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityType;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class FineractEntityAccessUtil {
    private final PlatformSecurityContext context;
    private final GlobalConfigurationRepositoryWrapper globalConfigurationRepository;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final CodeValueRepositoryWrapper codeValueRepository;
    private final FineractEntityAccessWriteService fineractEntityAccessWriteService;
    private final FineractEntityAccessReadService fineractEntityAccessReadService;
    private final FineractEntityRelationRepositoryWrapper fineractEntityRelationRepositoryWrapper;
    private final FineractEntityToEntityMappingRepository fineractEntityToEntityMappingRepository;
    @Autowired
    public FineractEntityAccessUtil(final PlatformSecurityContext context,
            final GlobalConfigurationRepositoryWrapper globalConfigurationRepository,
            final FineractEntityAccessWriteService fineractEntityAccessWriteService,
            final CodeValueReadPlatformService codeValueReadPlatformService, final CodeValueRepositoryWrapper codeValueRepository,
            final FineractEntityAccessReadService fineractEntityAccessReadService,
            final FineractEntityRelationRepositoryWrapper fineractEntityRelationRepositoryWrapper,
            final FineractEntityToEntityMappingRepository fineractEntityToEntityMappingRepository) {
        this.context = context;
        this.globalConfigurationRepository = globalConfigurationRepository;
        this.fineractEntityAccessWriteService = fineractEntityAccessWriteService;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.codeValueRepository = codeValueRepository;
        this.fineractEntityAccessReadService = fineractEntityAccessReadService;
        this.fineractEntityRelationRepositoryWrapper = fineractEntityRelationRepositoryWrapper;
        this.fineractEntityToEntityMappingRepository = fineractEntityToEntityMappingRepository;
    }
    @Transactional
    public void checkConfigurationAndAddProductResrictionsForUserOffice(final FineractEntityAccessType fineractEntityAccessType,
            final Long productOrChargeId) {
        AppUser thisUser = this.context.authenticatedUser();
        final GlobalConfigurationProperty property = this.globalConfigurationRepository
                .findOneByNameWithNotFoundDetection(FineractEntityAccessConstants.GLOBAL_CONFIG_FOR_OFFICE_SPECIFIC_PRODUCTS);
        if (property.isEnabled()) {
            final GlobalConfigurationProperty restrictToUserOfficeProperty = this.globalConfigurationRepository
                    .findOneByNameWithNotFoundDetection(FineractEntityAccessConstants.GLOBAL_CONFIG_FOR_RESTRICT_PRODUCTS_TO_USER_OFFICE);
            if (restrictToUserOfficeProperty.isEnabled()) {
                final Long officeId = thisUser.getOffice().getId();
                LocalDate startDateFormapping = null;
                LocalDate endDateFormapping = null;
                FineractEntityRelation fineractEntityRelation = fineractEntityRelationRepositoryWrapper
                        .findOneByCodeName(fineractEntityAccessType.toStr());
                Long relId = fineractEntityRelation.getId();
                final FineractEntityRelation mapId = this.fineractEntityRelationRepositoryWrapper.findOneWithNotFoundDetection(relId);
                final FineractEntityToEntityMapping newMap = FineractEntityToEntityMapping.newMap(mapId, officeId, productOrChargeId,
                        startDateFormapping, endDateFormapping);
                this.fineractEntityToEntityMappingRepository.save(newMap);
            }
        }
    }
    public String getSQLWhereClauseForProductIDsForUserOffice_ifGlobalConfigEnabled(FineractEntityType fineractEntityType) {
        String inClause = "";
        final GlobalConfigurationProperty property = this.globalConfigurationRepository
                .findOneByNameWithNotFoundDetection(FineractEntityAccessConstants.GLOBAL_CONFIG_FOR_OFFICE_SPECIFIC_PRODUCTS);
        if (property.isEnabled()) {
            if (fineractEntityType.equals(FineractEntityType.SAVINGS_PRODUCT)) {
                inClause = fineractEntityAccessReadService
                        .getSQLQueryInClauseIDList_ForSavingsProductsForOffice(this.context.authenticatedUser().getOffice().getId(), false);
            } else if (fineractEntityType.equals(FineractEntityType.LOAN_PRODUCT)) {
                inClause = fineractEntityAccessReadService
                        .getSQLQueryInClauseIDList_ForLoanProductsForOffice(this.context.authenticatedUser().getOffice().getId(), false);
            } else if (fineractEntityType.equals(FineractEntityType.CHARGE)) {
                inClause = fineractEntityAccessReadService
                        .getSQLQueryInClauseIDList_ForChargesForOffice(this.context.authenticatedUser().getOffice().getId(), false);
            }
        }
        return inClause;
    }
}
