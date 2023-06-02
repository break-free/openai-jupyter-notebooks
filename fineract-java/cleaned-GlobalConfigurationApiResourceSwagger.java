
package org.apache.fineract.infrastructure.configuration.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.apache.fineract.infrastructure.configuration.data.GlobalConfigurationPropertyData;
final class GlobalConfigurationApiResourceSwagger {
    private GlobalConfigurationApiResourceSwagger() {
    }
    @Schema(description = "GetGlobalConfigurationsResponse")
    public static final class GetGlobalConfigurationsResponse {
        private GetGlobalConfigurationsResponse() {}
        public List<GlobalConfigurationPropertyData> globalConfiguration;
    }
    @Schema(description = "PutGlobalConfigurationsRequest")
    public static final class PutGlobalConfigurationsRequest {
        private PutGlobalConfigurationsRequest() {}
        @Schema(example = "true")
        public boolean enabled;
        @Schema(example = "2")
        public Long description;
    }
    @Schema(description = "PutGlobalConfigurationsResponse")
    public static final class PutGlobalConfigurationsResponse {
        private PutGlobalConfigurationsResponse() {}
        static final class PutGlobalConfigurationsResponsechangesSwagger {
            private PutGlobalConfigurationsResponsechangesSwagger() {}
            @Schema(example = "true")
            public boolean enabled;
        }
        @Schema(example = "4")
        public Long resourceId;
        public PutGlobalConfigurationsResponsechangesSwagger changes;
    }
}
