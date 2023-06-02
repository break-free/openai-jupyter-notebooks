
package org.apache.fineract.infrastructure.configuration.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class ExternalServicesConfigurationApiResourceSwagger {
    private ExternalServicesConfigurationApiResourceSwagger() {
    }
    @Schema(description = "PutExternalServiceRequest")
    public static final class PutExternalServiceRequest {
        private PutExternalServiceRequest() {
        }
        @Schema(example = "test@mifos.org")
        public String username;
        @Schema(example = "XXXX")
        public String password;
    }
}
