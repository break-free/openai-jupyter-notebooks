
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.data.DepositProductData;
public interface DepositProductReadPlatformService {
    Collection<DepositProductData> retrieveAll(DepositAccountType depositAccountType);
    Collection<DepositProductData> retrieveAllForLookup(DepositAccountType depositAccountType);
    DepositProductData retrieveOne(DepositAccountType depositAccountType, Long productId);
    DepositProductData retrieveOneWithChartSlabs(DepositAccountType depositAccountType, Long productId);
}
