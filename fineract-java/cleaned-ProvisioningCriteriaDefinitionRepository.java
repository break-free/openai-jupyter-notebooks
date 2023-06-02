
package org.apache.fineract.organisation.provisioning.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ProvisioningCriteriaDefinitionRepository
        extends JpaRepository<ProvisioningCriteriaDefinition, Long>, JpaSpecificationExecutor<ProvisioningCriteriaDefinition> {
}
