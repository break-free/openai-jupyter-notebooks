
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.client.data.ClientChargeData;
public interface ClientChargeReadPlatformService {
    Page<ClientChargeData> retrieveClientCharges(Long clientId, String status, Boolean pendingPayment, SearchParameters parameters);
    ClientChargeData retrieveClientCharge(Long clientId, Long clientChargeId);
}
