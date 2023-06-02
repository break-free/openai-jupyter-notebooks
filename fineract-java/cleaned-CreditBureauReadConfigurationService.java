
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import java.util.Map;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauConfigurationData;
public interface CreditBureauReadConfigurationService {
    Collection<CreditBureauConfigurationData> readConfigurationByOrganisationCreditBureauId(long id);
    Map<String, String> retrieveConfigMap(long id);
}
