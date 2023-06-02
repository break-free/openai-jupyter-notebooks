
package org.apache.fineract.portfolio.client.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.client.data.ClientTransactionData;
import org.springframework.transaction.annotation.Transactional;
public interface ClientTransactionReadPlatformService {
    @Transactional(readOnly = true)
    Page<ClientTransactionData> retrieveAllTransactions(Long clientId, SearchParameters parameters);
    @Transactional(readOnly = true)
    Collection<ClientTransactionData> retrieveAllTransactions(Long clientId, Long chargeId);
    @Transactional(readOnly = true)
    ClientTransactionData retrieveTransaction(Long clientId, Long transactionId);
}
