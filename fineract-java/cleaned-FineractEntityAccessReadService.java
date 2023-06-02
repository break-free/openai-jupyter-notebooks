
package org.apache.fineract.infrastructure.entityaccess.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.entityaccess.data.FineractEntityRelationData;
import org.apache.fineract.infrastructure.entityaccess.data.FineractEntityToEntityMappingData;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityType;
public interface FineractEntityAccessReadService {
    Collection<FineractEntityToEntityMappingData> retrieveEntityAccessFor(FineractEntityType firstEntityType, Long relId, Long fromEntityId,
            boolean includeAllSubOffices);
    String getSQLQueryInClause_WithListOfIDsForEntityAccess(FineractEntityType firstEntityType, Long relId, Long fromEntityId,
            boolean includeAllOffices);
    String getSQLQueryInClauseIDList_ForLoanProductsForOffice(Long loanProductId, boolean includeAllOffices);
    String getSQLQueryInClauseIDList_ForSavingsProductsForOffice(Long savingsProductId, boolean includeAllOffices);
    String getSQLQueryInClauseIDList_ForChargesForOffice(Long officeId, boolean includeAllOffices);
    Collection<FineractEntityRelationData> retrieveAllSupportedMappingTypes();
    Collection<FineractEntityToEntityMappingData> retrieveOneMapping(Long mapId);
    Collection<FineractEntityToEntityMappingData> retrieveEntityToEntityMappings(Long mapId, Long fromoId, Long toId);
}
