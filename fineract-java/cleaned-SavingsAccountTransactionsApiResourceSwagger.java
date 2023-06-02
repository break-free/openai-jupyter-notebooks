
package org.apache.fineract.portfolio.savings.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class SavingsAccountTransactionsApiResourceSwagger {
    private SavingsAccountTransactionsApiResourceSwagger() {}
    @Schema(description = "PostSavingsAccountTransactionsRequest")
    public static final class PostSavingsAccountTransactionsRequest {
        private PostSavingsAccountTransactionsRequest() {}
        @Schema(example = "27 March 2022")
        public String transactionDate;
        @Schema(example = "1000")
        public Integer transactionAmount;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "true")
        public String lienAllowed;
        @Schema(example = "String")
        public String reasonForBlock;
    }
    @Schema(description = "PostSavingsAccountTransactionsResponse")
    public static final class PostSavingsAccountTransactionsResponse {
        private PostSavingsAccountTransactionsResponse() {}
        @Schema(example = "1")
        public Integer officeId;
        @Schema(example = "1")
        public Integer clientId;
        @Schema(example = "1")
        public Integer savingsId;
        @Schema(example = "1")
        public Integer resourceId;
    }
    @Schema(description = "PostSavingsAccountBulkReversalTransactionsRequest")
    public static final class PostSavingsAccountBulkReversalTransactionsRequest {
        private PostSavingsAccountBulkReversalTransactionsRequest() {}
        @Schema(example = "true")
        public String isBulk;
    }
}
