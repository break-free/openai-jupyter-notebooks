
package org.apache.fineract.portfolio.savings.service;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.data.DepositAccountData;
public interface DepositAccountPreMatureCalculationPlatformService {
    DepositAccountData calculatePreMatureAmount(Long accountId, JsonQuery query, DepositAccountType depositAccountType);
}
