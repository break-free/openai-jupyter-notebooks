
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauData;
public interface CreditBureauReadPlatformService {
    Collection<CreditBureauData> retrieveCreditBureau();
}
