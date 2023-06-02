
package org.apache.fineract.organisation.provisioning.service;
import java.util.Collection;
import org.apache.fineract.organisation.provisioning.data.ProvisioningCategoryData;
public interface ProvisioningCategoryReadPlatformService {
    Collection<ProvisioningCategoryData> retrieveAllProvisionCategories();
}
