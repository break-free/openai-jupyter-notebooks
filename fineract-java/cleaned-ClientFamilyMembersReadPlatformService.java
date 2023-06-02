
package org.apache.fineract.portfolio.client.service;
import java.util.Collection;
import org.apache.fineract.portfolio.client.data.ClientFamilyMembersData;
public interface ClientFamilyMembersReadPlatformService {
    Collection<ClientFamilyMembersData> getClientFamilyMembers(long clientId);
    ClientFamilyMembersData getClientFamilyMember(long id);
    ClientFamilyMembersData retrieveTemplate();
}
