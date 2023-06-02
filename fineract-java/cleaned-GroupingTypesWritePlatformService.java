
package org.apache.fineract.portfolio.group.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GroupingTypesWritePlatformService {
    CommandProcessingResult createCenter(JsonCommand command);
    CommandProcessingResult updateCenter(Long entityId, JsonCommand command);
    CommandProcessingResult createGroup(Long centerId, JsonCommand command);
    CommandProcessingResult activateGroupOrCenter(Long entityId, JsonCommand command);
    CommandProcessingResult updateGroup(Long groupId, JsonCommand command);
    CommandProcessingResult deleteGroup(Long groupId);
    CommandProcessingResult closeGroup(Long groupId, JsonCommand command);
    CommandProcessingResult closeCenter(Long centerId, JsonCommand command);
    CommandProcessingResult unassignGroupOrCenterStaff(Long groupId, JsonCommand command);
    CommandProcessingResult assignGroupOrCenterStaff(Long groupId, JsonCommand command);
    CommandProcessingResult associateClientsToGroup(Long groupId, JsonCommand command);
    CommandProcessingResult disassociateClientsFromGroup(Long groupId, JsonCommand command);
    CommandProcessingResult associateGroupsToCenter(Long centerId, JsonCommand command);
    CommandProcessingResult disassociateGroupsToCenter(Long centerId, JsonCommand command);
}
