
package org.apache.fineract.portfolio.client.service;
import java.util.Collection;
import org.apache.fineract.portfolio.address.data.ClientAddressData;
public interface ClientAddressReadPlatformService {
    Collection<ClientAddressData> retrieveClientAddrConfiguration(String entity);
}
