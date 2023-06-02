
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauMasterData;
public interface CreditBureauMasterReadPlatformService {
    Collection<CreditBureauMasterData> retrieveCreditBureauByCountry(String country);
    Collection<CreditBureauMasterData> retrieveCreditBureauByCountry();
}
