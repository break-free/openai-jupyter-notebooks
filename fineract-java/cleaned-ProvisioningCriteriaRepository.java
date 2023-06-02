
package org.apache.fineract.organisation.provisioning.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ProvisioningCriteriaRepository
        extends JpaRepository<ProvisioningCriteria, Long>, JpaSpecificationExecutor<ProvisioningCriteria> {
}
