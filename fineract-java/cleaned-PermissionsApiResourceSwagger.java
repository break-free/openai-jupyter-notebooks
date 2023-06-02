
package org.apache.fineract.useradministration.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class PermissionsApiResourceSwagger {
    private PermissionsApiResourceSwagger() {
    }
    @Schema(description = "GetPermissionsResponse")
    public static final class GetPermissionsResponse {
        private GetPermissionsResponse() {
        }
        @Schema(example = "authorisation")
        public String grouping;
        @Schema(example = "READ_PERMISSION")
        public String code;
        @Schema(example = "PERMISSION")
        public String entityName;
        @Schema(example = "READ")
        public String actionName;
        @Schema(example = "true")
        public Boolean selected;
    }
    @Schema(description = "PutPermissionsRequest")
    public static final class PutPermissionsRequest {
        private PutPermissionsRequest() {
        }
        @Schema(example = "\"CREATE_GUARANTOR\":true,\n" + "    \"CREATE_CLIENT\":true")
        public String permissions;
    }
}
