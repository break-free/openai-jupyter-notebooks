
package org.apache.fineract.portfolio.self.security.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class SelfUserApiResourceSwagger {
    private SelfUserApiResourceSwagger() {}
    @Schema(description = "PutSelfUserRequest")
    public static final class PutSelfUserRequest {
        private PutSelfUserRequest() {}
        @Schema(example = "Abcd1234")
        public String password;
        @Schema(example = "Abcd1234")
        public String repeatPassword;
    }
    @Schema(description = "PutSelfUserResponse")
    public static final class PutSelfUserResponse {
        private PutSelfUserResponse() {}
        static final class PutSelfUserChanges {
            private PutSelfUserChanges() {}
            @Schema(example = "6a72a630795be86fe926ce540fc45b6b922fe5ba130f185fe806a26b5e5efcdd")
            public String passwordEncoded;
        }
        @Schema(example = "1")
        public Integer officeId;
        @Schema(example = "6")
        public Integer resourceId;
        public PutSelfUserChanges changes;
    }
}
