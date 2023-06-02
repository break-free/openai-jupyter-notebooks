
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import org.apache.fineract.portfolio.savings.data.SavingsProductData;
public interface SavingsProductReadPlatformService {
    Collection<SavingsProductData> retrieveAll();
    Collection<SavingsProductData> retrieveAllForLookup();
    Collection<SavingsProductData> retrieveAllForLookupByType(Boolean isOverdraftType);
    Collection<SavingsProductData> retrieveAllForCurrency(String currencyCode);
    SavingsProductData retrieveOne(Long productId);
}
