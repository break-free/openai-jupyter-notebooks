
package org.apache.fineract.portfolio.address.api;
import io.swagger.v3.oas.annotations.media.Schema;
@SuppressWarnings({ "MemberName" })
final class EntityFieldConfigurationApiResourcesSwagger {
    private EntityFieldConfigurationApiResourcesSwagger() {}
    @Schema(description = "GetFieldConfigurationEntityResponse")
    public static final class GetFieldConfigurationEntityResponse {
        private GetFieldConfigurationEntityResponse() {}
        @Schema(example = "1")
        public Integer fieldConfigurationId;
        @Schema(example = "ADDRESS")
        public String entity;
        @Schema(example = "CLIENT")
        public String subentity;
        @Schema(example = "addressType")
        public String field;
        @Schema(example = "true")
        public String is_enabled;
        @Schema(example = "false")
        public String is_mandatory;
        @Schema(example = " ")
        public String validation_regex;
    }
}
