
package org.apache.fineract.portfolio.client.service;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.transaction.annotation.Transactional;
public interface ClientTransactionWritePlatformService {
    @Transactional
    CommandProcessingResult undo(Long clientId, Long transactionId);
}
