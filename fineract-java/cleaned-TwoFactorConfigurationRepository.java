
package org.apache.fineract.infrastructure.security.domain;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@ConditionalOnProperty("fineract.security.2fa.enabled")
public interface TwoFactorConfigurationRepository
        extends JpaRepository<TwoFactorConfiguration, Long>, JpaSpecificationExecutor<TwoFactorConfiguration> {
    TwoFactorConfiguration findByName(String name);
    @Override
    List<TwoFactorConfiguration> findAll();
}
