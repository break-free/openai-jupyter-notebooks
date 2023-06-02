
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.savings.data.DepositAccountOnHoldTransactionData;
public interface DepositAccountOnHoldTransactionReadPlatformService {
    Page<DepositAccountOnHoldTransactionData> retriveAll(Long savingsId, Long guarantorFundingId, SearchParameters searchParameters);
}
