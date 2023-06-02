
package org.apache.fineract.infrastructure.codes.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class CodeValuesApiResourceSwagger {
    private CodeValuesApiResourceSwagger() {
    }
    @Schema(description = "GetCodeValuesDataResponse")
    public static final class GetCodeValuesDataResponse {
        private GetCodeValuesDataResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Passport")
        public String name;
        @Schema(example = "Passport information")
        public String description;
        @Schema(example = "0")
        public Integer position;
    }
    @Schema(description = "PostCodeValuesDataRequest")
    public static final class PostCodeValuesDataRequest {
        private PostCodeValuesDataRequest() {
        }
        @Schema(example = "Passport")
        public String name;
        @Schema(example = "true")
        public Boolean isActive;
        @Schema(example = "Passport information")
        public String description;
        @Schema(example = "0")
        public Integer position;
    }
    @Schema(description = "PostCodeValueDataResponse")
    public static final class PostCodeValueDataResponse {
        private PostCodeValueDataResponse() {
        }
        @Schema(example = "4")
        public Long resourceId;
    }
    @Schema(description = "PutCodeValuesDataRequest")
    public static final class PutCodeValuesDataRequest {
        private PutCodeValuesDataRequest() {
        }
        @Schema(example = "Passport")
        public String name;
        @Schema(example = "Passport information")
        public String description;
        @Schema(example = "0")
        public Integer position;
    }
    @Schema(description = "PutCodeValueDataResponse")
    public static final class PutCodeValueDataResponse {
        private PutCodeValueDataResponse() {
        }
        private static final class PutCodeValuechangesSwagger {
            private PutCodeValuechangesSwagger() {}
            @Schema(example = "Passport")
            public String name;
            @Schema(example = "Passport information")
            public String description;
            @Schema(example = "0")
            public Integer position;
        }
        @Schema(example = "4")
        public Long resourceId;
        public PutCodeValuechangesSwagger changes;
    }
    @Schema(description = "DeleteCodeValueDataResponse")
    public static final class DeleteCodeValueDataResponse {
        private DeleteCodeValueDataResponse() {
        }
        @Schema(example = "4")
        public Long resourceId;
    }
}
