
package org.apache.fineract.infrastructure.security.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.useradministration.data.RoleData;
final class AuthenticationApiResourceSwagger {
    private AuthenticationApiResourceSwagger() {
    }
    @Schema(description = "PostAuthenticationRequest")
    public static final class PostAuthenticationRequest {
        private PostAuthenticationRequest() {
        }
        @Schema(required = true, example = "mifos")
        public String username;
        @Schema(required = true, example = "password")
        public String password;
    }
    @Schema(description = "PostAuthenticationResponse")
    public static final class PostAuthenticationResponse {
        private PostAuthenticationResponse() {
        }
        @Schema(example = "mifos")
        public String username;
        @Schema(example = "1")
        public Long userId;
        @Schema(example = "bWlmb3M6cGFzc3dvcmQ=")
        public String base64EncodedAuthenticationKey;
        @Schema(example = "true")
        public boolean authenticated;
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "Head Office")
        public String officeName;
        @Schema(example = "1")
        public Long staffId;
        @Schema(example = "Director, Program")
        public String staffDisplayName;
        public EnumOptionData organisationalRole;
        public Collection<RoleData> roles;
        @Schema(example = "ALL_FUNCTIONS")
        public Collection<String> permissions;
    }
}
