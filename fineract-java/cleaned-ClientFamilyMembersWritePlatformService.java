
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.domain.Client;
public interface ClientFamilyMembersWritePlatformService {
    CommandProcessingResult addFamilyMember(long clientId, JsonCommand command);
    CommandProcessingResult addClientFamilyMember(Client client, JsonCommand command);
    CommandProcessingResult updateFamilyMember(Long familyMemberId, JsonCommand command);
    CommandProcessingResult deleteFamilyMember(Long familyMemberId, JsonCommand command);
}
