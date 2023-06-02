
package org.apache.fineract.infrastructure.core.domain;
import java.util.Optional;
import org.apache.fineract.useradministration.domain.AppUser;
import org.apache.fineract.useradministration.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Autowired
    private AppUserRepository userRepository;
    @Override
    public Optional<Long> getCurrentAuditor() {
        Optional<Long> currentUserId;
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            final Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                currentUserId = Optional.ofNullable(((AppUser) authentication.getPrincipal()).getId());
            } else {
                currentUserId = retrieveSuperUser();
            }
        } else {
            currentUserId = retrieveSuperUser();
        }
        return currentUserId;
    }
    private Optional<Long> retrieveSuperUser() {
        return Optional.of(1L);
    }
}
