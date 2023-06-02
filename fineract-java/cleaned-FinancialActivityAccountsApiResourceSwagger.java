
package org.apache.fineract.accounting.financialactivityaccount.api;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.fineract.accounting.financialactivityaccount.data.FinancialActivityData;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
final class FinancialActivityAccountsApiResourceSwagger {
    private FinancialActivityAccountsApiResourceSwagger() {}
    @Schema(description = "GetFinancialActivityAccountsResponse")
    public static final class GetFinancialActivityAccountsResponse {
        private GetFinancialActivityAccountsResponse() {
        }
        @Schema(example = "1")
        public Long id;
        public FinancialActivityData financialActivityData;
        public GLAccountData glAccountData;
    }
    @Schema(description = "PostFinancialActivityAccountsRequest")
    public static final class PostFinancialActivityAccountsRequest {
        private PostFinancialActivityAccountsRequest() {
        }
        @Schema(example = "200")
        public Long financialActivityId;
        @Schema(example = "2")
        public Long glAccountId;
    }
    @Schema(description = "PostFinancialActivityAccountsResponse")
    public static final class PostFinancialActivityAccountsResponse {
        private PostFinancialActivityAccountsResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "PutFinancialActivityAccountsRequest")
    public static final class PutFinancialActivityAccountsRequest {
        private PutFinancialActivityAccountsRequest() {
        }
        @Schema(example = "200")
        public Long financialActivityId;
        @Schema(example = "3")
        public Long glAccountId;
    }
    @Schema(description = "PutFinancialActivityAccountsResponse")
    public static final class PutFinancialActivityAccountsResponse {
        private PutFinancialActivityAccountsResponse() {
        }
        public static final class PutFinancialActivityAccountscommentsSwagger {
            private PutFinancialActivityAccountscommentsSwagger() {}
            @Schema(example = "1")
            public Long glAccountId;
        }
        @Schema(example = "1")
        public Long resourceId;
        public PutFinancialActivityAccountscommentsSwagger comments;
    }
    @Schema(description = "DeleteFinancialActivityAccountsResponse")
    public static final class DeleteFinancialActivityAccountsResponse {
        private DeleteFinancialActivityAccountsResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
