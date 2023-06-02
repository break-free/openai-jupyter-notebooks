
package org.apache.fineract.portfolio.collectionsheet.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
@SuppressWarnings({ "MemberName" })
final class CollectionSheetApiResourceSwagger {
    private CollectionSheetApiResourceSwagger() {}
    @Schema(description = "PostCollectionSheetRequest")
    public static final class PostCollectionSheetRequest {
        private PostCollectionSheetRequest() {}
        static final class PostCollectionSheetBulkRepaymentTransactions {
            private PostCollectionSheetBulkRepaymentTransactions() {}
            @Schema(example = "10")
            public Integer loanId;
            @Schema(example = "1221.36")
            public Double transactionAmount;
            @Schema(example = "19")
            public Integer paymentTypeId;
            @Schema(example = "1245356")
            public Long receiptNumber;
        }
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "04 May 2014")
        public String transactionDate;
        @Schema(example = "04 May 2014")
        public String actualDisbursementDate;
        public List<Integer> bulkDisbursementTransactions;
        public PostCollectionSheetBulkRepaymentTransactions bulkRepaymentTransactions;
        public List<Integer> bulkSavingsDueTransactions;
    }
    @Schema(description = "PostCollectionSheetResponse")
    public static final class PostCollectionSheetResponse {
        private PostCollectionSheetResponse() {}
        static final class PostCollectionSheetChanges {
            private PostCollectionSheetChanges() {}
            @Schema(example = "en")
            public String locale;
            @Schema(example = "dd MMMM yyyy")
            public String dateFormat;
            @Schema(example = "[15]")
            public List<Integer> loanTransactions;
            @Schema(example = "[]")
            public List<Integer> SavingsTransactions;
        }
        @Schema(example = "10")
        public Integer groupId;
        @Schema(example = "10")
        public Integer resourceId;
        public PostCollectionSheetChanges changes;
    }
}
