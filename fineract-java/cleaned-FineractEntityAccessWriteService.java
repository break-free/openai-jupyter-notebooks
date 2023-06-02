
package org.apache.fineract.infrastructure.entityaccess.service;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface FineractEntityAccessWriteService {
    CommandProcessingResult createEntityAccess(JsonCommand command);
    CommandProcessingResult createEntityToEntityMapping(Long relId, JsonCommand command);
    CommandProcessingResult updateEntityToEntityMapping(Long mapId, JsonCommand command);
    CommandProcessingResult deleteEntityToEntityMapping(Long mapId);
    void addNewEntityAccess(String entityType, Long entityId, CodeValue accessType, String secondEntityType, Long secondEntityId);
}
