
package org.apache.fineract.infrastructure.core.config;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SecurityValidationConfig {
    @Value("${fineract.security.basicauth.enabled}")
    private Boolean basicAuthEnabled;
    @Value("${fineract.security.oauth.enabled}")
    private Boolean oauthEnabled;
    @PostConstruct
    public void validate() {
        if (!Boolean.TRUE.equals(basicAuthEnabled) && !Boolean.TRUE.equals(oauthEnabled)) {
            throw new IllegalArgumentException(
                    "No authentication scheme selected. Please decide if you want to use basic OR OAuth2 authentication.");
        }
        if (Boolean.TRUE.equals(basicAuthEnabled) && Boolean.TRUE.equals(oauthEnabled)) {
            throw new IllegalArgumentException(
                    "Too many authentication schemes selected. Please decide if you want to use basic OR OAuth2 authentication.");
        }
    }
}
