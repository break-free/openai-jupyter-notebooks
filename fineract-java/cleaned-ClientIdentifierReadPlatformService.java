
package org.apache.fineract.portfolio.client.service;
import java.util.Collection;
import org.apache.fineract.portfolio.client.data.ClientIdentifierData;
public interface ClientIdentifierReadPlatformService {
    Collection<ClientIdentifierData> retrieveClientIdentifiers(Long clientId);
    ClientIdentifierData retrieveClientIdentifier(Long clientId, Long clientIdentifierId);
}
