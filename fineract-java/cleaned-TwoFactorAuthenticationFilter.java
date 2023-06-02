
package org.apache.fineract.infrastructure.security.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.fineract.infrastructure.security.constants.TwoFactorConstants;
import org.apache.fineract.infrastructure.security.data.FineractJwtAuthenticationToken;
import org.apache.fineract.infrastructure.security.domain.TFAccessToken;
import org.apache.fineract.infrastructure.security.service.TwoFactorService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;
@Service
@ConditionalOnProperty("fineract.security.2fa.enabled")
public class TwoFactorAuthenticationFilter extends GenericFilterBean {
    private final TwoFactorService twoFactorService;
    @Autowired
    public TwoFactorAuthenticationFilter(TwoFactorService twoFactorService) {
        this.twoFactorService = twoFactorService;
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = null;
        if (context != null) {
            authentication = context.getAuthentication();
        }
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AppUser) {
            AppUser user = (AppUser) authentication.getPrincipal();
            if (user == null) {
                return;
            }
            if (!user.hasSpecificPermissionTo(TwoFactorConstants.BYPASS_TWO_FACTOR_PERMISSION)) {
                String token = request.getHeader("Fineract-Platform-TFA-Token");
                if (token != null) {
                    TFAccessToken accessToken = twoFactorService.fetchAccessTokenForUser(user, token);
                    if (accessToken == null || !accessToken.isValid()) {
                        response.addHeader("WWW-Authenticate", "Basic realm=\"Fineract Platform API Two Factor\"");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid two-factor access token provided");
                        return;
                    }
                } else {
                    chain.doFilter(req, res);
                    return;
                }
            }
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
            updatedAuthorities.add(new SimpleGrantedAuthority("TWOFACTOR_AUTHENTICATED"));
            context.setAuthentication(createUpdatedAuthentication(authentication, updatedAuthorities));
        }
        chain.doFilter(req, res);
    }
    private Authentication createUpdatedAuthentication(final Authentication currentAuthentication,
            final List<GrantedAuthority> updatedAuthorities) throws ServletException {
        if (currentAuthentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken updatedAuthentication = new UsernamePasswordAuthenticationToken(
                    currentAuthentication.getPrincipal(), currentAuthentication.getCredentials(), updatedAuthorities);
            return updatedAuthentication;
        } else if (currentAuthentication instanceof FineractJwtAuthenticationToken) {
            FineractJwtAuthenticationToken fineractJwtAuthenticationToken = (FineractJwtAuthenticationToken) currentAuthentication;
            FineractJwtAuthenticationToken updatedAuthentication = new FineractJwtAuthenticationToken(
                    fineractJwtAuthenticationToken.getToken(), (Collection<GrantedAuthority>) updatedAuthorities,
                    (UserDetails) currentAuthentication.getPrincipal());
            return updatedAuthentication;
        } else {
            throw new ServletException("Unknown authentication type: " + currentAuthentication.getClass().getName());
        }
    }
}
