
package org.apache.fineract.infrastructure.security.data;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
public class FineractJwtAuthenticationToken extends JwtAuthenticationToken {
    private final UserDetails user;
    public FineractJwtAuthenticationToken(Jwt jwt, Collection<GrantedAuthority> authorities, UserDetails user) {
        super(jwt, authorities, user.getUsername());
        this.user = Objects.requireNonNull(user, "user");
    }
    @Override
    public UserDetails getPrincipal() {
        return user;
    }
}
