
package org.apache.fineract.portfolio.client.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.client.data.ClientData;
public interface ClientReadPlatformService {
    ClientData retrieveTemplate(Long officeId, boolean staffInSelectedOfficeOnly);
    Page<ClientData> retrieveAll(SearchParameters searchParameters);
    ClientData retrieveOne(Long clientId);
    Collection<ClientData> retrieveAllForLookup(String extraCriteria);
    Collection<ClientData> retrieveAllForLookupByOfficeId(Long officeId);
    ClientData retrieveClientByIdentifier(Long identifierTypeId, String identifierKey);
    Collection<ClientData> retrieveClientMembersOfGroup(Long groupId);
    Collection<ClientData> retrieveActiveClientMembersOfGroup(Long groupId);
    Collection<ClientData> retrieveActiveClientMembersOfCenter(Long centerId);
    ClientData retrieveAllNarrations(String clientNarrations);
    Collection<Long> retrieveUserClients(Long aUserID);
    LocalDate retrieveClientTransferProposalDate(Long clientId);
    void validateClient(Long clientId);
}
