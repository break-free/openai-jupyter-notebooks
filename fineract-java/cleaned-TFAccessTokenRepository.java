
package org.apache.fineract.infrastructure.security.domain;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@ConditionalOnProperty("fineract.security.2fa.enabled")
public interface TFAccessTokenRepository extends JpaRepository<TFAccessToken, Long>, JpaSpecificationExecutor<TFAccessToken> {
    TFAccessToken findByUserAndToken(AppUser user, String token);
}
