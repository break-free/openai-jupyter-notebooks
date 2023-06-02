
package org.apache.fineract.infrastructure.core.domain;
import org.apache.fineract.infrastructure.security.domain.PlatformUser;
import org.apache.fineract.infrastructure.security.service.PlatformPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service(value = "applicationPasswordEncoder")
@Scope("singleton")
public class DefaultPlatformPasswordEncoder implements PlatformPasswordEncoder {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public DefaultPlatformPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String encode(final PlatformUser appUser) {
        return this.passwordEncoder.encode(appUser.getPassword());
    }
}
