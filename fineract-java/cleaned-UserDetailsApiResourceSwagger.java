
package org.apache.fineract.infrastructure.security.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.useradministration.data.RoleData;
final class UserDetailsApiResourceSwagger {
    private UserDetailsApiResourceSwagger() {
    }
    @Schema(description = "GetUserDetailsResponse")
    public static final class GetUserDetailsResponse {
        private GetUserDetailsResponse() {
        }
        @Schema(example = "mifos")
        public String username;
        @Schema(example = "1")
        public Long userId;
        @Schema(example = "bWlmb3M6cGFzc3dvcmQ=")
        public String accessToken;
        @Schema(example = "true")
        public boolean authenticated;
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "Head Office")
        public String officeName;
        @Schema(example = "1")
        public Long staffId;
        @Schema(example = "mifosStaffDisplayName")
        public String staffDisplayName;
        public EnumOptionData organisationalRole;
        public Collection<RoleData> roles;
        @Schema(example = "ALL_FUNCTIONS")
        public Collection<String> permissions;
    }
}
