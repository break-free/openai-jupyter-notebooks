
package org.apache.fineract.infrastructure.campaigns.email.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface EmailConfigurationRepository
        extends JpaRepository<EmailConfiguration, Long>, JpaSpecificationExecutor<EmailConfiguration> {
    EmailConfiguration findByName(String name);
}
