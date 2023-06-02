
package org.apache.fineract.infrastructure.security.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.fineract.infrastructure.security.data.FineractJwtAuthenticationToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Service
@ConditionalOnProperty(name = "fineract.security.2fa.enabled", havingValue = "false")
public class InsecureTwoFactorAuthenticationFilter extends TwoFactorAuthenticationFilter {
    public InsecureTwoFactorAuthenticationFilter() {
        super(null);
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = null;
        if (context != null) {
            authentication = context.getAuthentication();
        }
        if (authentication != null && authentication.isAuthenticated()) {
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
            updatedAuthorities.add(new SimpleGrantedAuthority("TWOFACTOR_AUTHENTICATED"));
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken updatedAuthentication = new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);
                context.setAuthentication(updatedAuthentication);
            } else if (authentication instanceof FineractJwtAuthenticationToken) {
                FineractJwtAuthenticationToken fineractJwtAuthenticationToken = (FineractJwtAuthenticationToken) authentication;
                FineractJwtAuthenticationToken updatedAuthentication = new FineractJwtAuthenticationToken(
                        fineractJwtAuthenticationToken.getToken(), (Collection<GrantedAuthority>) updatedAuthorities,
                        (UserDetails) authentication.getPrincipal());
                context.setAuthentication(updatedAuthentication);
            }
        }
        chain.doFilter(req, res);
    }
}
