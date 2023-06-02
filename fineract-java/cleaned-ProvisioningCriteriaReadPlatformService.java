
package org.apache.fineract.organisation.provisioning.service;
import java.util.Collection;
import org.apache.fineract.organisation.provisioning.data.ProvisioningCriteriaData;
public interface ProvisioningCriteriaReadPlatformService {
    ProvisioningCriteriaData retrievePrivisiongCriteriaTemplate();
    ProvisioningCriteriaData retrieveProvisioningCriteria(Long criteriaId);
    Collection<ProvisioningCriteriaData> retrieveAllProvisioningCriterias();
    ProvisioningCriteriaData retrievePrivisiongCriteriaTemplate(ProvisioningCriteriaData data);
}
