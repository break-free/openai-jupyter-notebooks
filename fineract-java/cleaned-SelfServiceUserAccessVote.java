
package org.apache.fineract.infrastructure.security.vote;
import java.util.Collection;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
public class SelfServiceUserAccessVote implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(@SuppressWarnings("unused") ConfigAttribute attribute) {
        return true;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
    @Override
    public int vote(final Authentication authentication, final FilterInvocation fi,
            @SuppressWarnings("unused") final Collection<ConfigAttribute> attributes) {
        if (!"OPTIONS".equalsIgnoreCase(fi.getHttpRequest().getMethod())) {
            AppUser user = (AppUser) authentication.getPrincipal();
            String pathURL = fi.getRequestUrl();
            boolean isSelfServiceRequest = (pathURL != null && pathURL.contains("/self/"));
            boolean notAllowed = ((isSelfServiceRequest && !user.isSelfServiceUser())
                    || (!isSelfServiceRequest && user.isSelfServiceUser()));
            if (notAllowed) {
                return ACCESS_DENIED;
            }
        }
        return ACCESS_GRANTED;
    }
}
