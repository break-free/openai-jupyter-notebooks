
package org.apache.fineract.infrastructure.codes.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class CodesApiResourceSwagger {
    private CodesApiResourceSwagger() {
    }
    @Schema(description = "GetCodesResponse")
    public static final class GetCodesResponse {
        private GetCodesResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Education")
        public String name;
        @Schema(example = "true")
        public boolean systemDefined;
    }
    @Schema(description = "PostCodesRequest")
    public static final class PostCodesRequest {
        private PostCodesRequest() {
        }
        @Schema(example = "MyNewCode")
        public String name;
    }
    @Schema(description = "PostCodesResponse")
    public static final class PostCodesResponse {
        private PostCodesResponse() {
        }
        @Schema(example = "4")
        public Long resourceId;
    }
    @Schema(description = "PutCodesRequest")
    public static final class PutCodesRequest {
        private PutCodesRequest() {
        }
        @Schema(example = "MyNewCode(changed)")
        public String name;
    }
    @Schema(description = "PutCodesResponse")
    public static final class PutCodesResponse {
        private PutCodesResponse() {
        }
        private static final class PutCodesApichangesSwagger {
            private PutCodesApichangesSwagger() {}
            @Schema(example = "MyNewCode(changed)")
            public String name;
        }
        @Schema(example = "4")
        public Long resourceId;
        public PutCodesApichangesSwagger changes;
    }
    @Schema(description = "DeleteCodesResponse")
    public static final class DeleteCodesResponse {
        private DeleteCodesResponse() {
        }
        @Schema(example = "4")
        public Long resourceId;
    }
}
