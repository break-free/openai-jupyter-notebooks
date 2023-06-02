
package org.apache.fineract.useradministration.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class PasswordPreferencesApiResourceSwagger {
    private PasswordPreferencesApiResourceSwagger() {
    }
    @Schema(description = "GetPasswordPreferencesTemplateResponse")
    public static final class GetPasswordPreferencesTemplateResponse {
        private GetPasswordPreferencesTemplateResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Password must be at least 1 character and not more that 50 characters long")
        public String description;
        @Schema(example = "true")
        public boolean active;
        @Schema(example = "simple")
        public String key;
    }
    @Schema(description = "PutPasswordPreferencesTemplateRequest")
    public static final class PutPasswordPreferencesTemplateRequest {
        private PutPasswordPreferencesTemplateRequest() {
        }
        @Schema(example = "1")
        public Long validationPolicyId;
    }
}
