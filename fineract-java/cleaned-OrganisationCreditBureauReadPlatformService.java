
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.OrganisationCreditBureauData;
public interface OrganisationCreditBureauReadPlatformService {
    Collection<OrganisationCreditBureauData> retrieveOrgCreditBureau();
    OrganisationCreditBureauData retrieveOrgCreditBureauById(long orgCbId);
}
