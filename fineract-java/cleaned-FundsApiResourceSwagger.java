
package org.apache.fineract.portfolio.fund.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class FundsApiResourceSwagger {
    private FundsApiResourceSwagger() {}
    @Schema(description = "GetFundsResponse")
    public static final class GetFundsResponse {
        private GetFundsResponse() {}
        @Schema(example = "1")
        public Integer id;
        @Schema(example = "EU Agri Fund")
        public String name;
    }
    @Schema(description = "PostFundsRequest")
    public static final class PostFundsRequest {
        private PostFundsRequest() {}
        @Schema(example = "EU Agri Fund")
        public String name;
    }
    @Schema(description = "PostFundsResponse")
    public static final class PostFundsResponse {
        private PostFundsResponse() {}
        @Schema(example = "1")
        public Integer resourceId;
    }
    @Schema(description = "PutFundsFundIdRequest")
    public static final class PutFundsFundIdRequest {
        private PutFundsFundIdRequest() {}
        @Schema(example = "EU Agri Fund (2010-2020)")
        public String name;
    }
    @Schema(description = "PutFundsFundIdResponse")
    public static final class PutFundsFundIdResponse {
        private PutFundsFundIdResponse() {}
        @Schema(example = "1")
        public Integer resourceId;
        public PutFundsFundIdRequest changes;
    }
}
