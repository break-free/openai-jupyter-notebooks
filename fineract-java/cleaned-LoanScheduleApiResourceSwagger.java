
package org.apache.fineract.portfolio.loanaccount.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
final class LoanScheduleApiResourceSwagger {
    private LoanScheduleApiResourceSwagger() {}
    @Schema(description = "PostLoansLoanIdScheduleRequest")
    public static final class PostLoansLoanIdScheduleRequest {
        private PostLoansLoanIdScheduleRequest() {}
    }
    @Schema(description = "PostLoansLoanIdScheduleResponse")
    public static final class PostLoansLoanIdScheduleResponse {
        private PostLoansLoanIdScheduleResponse() {}
        static final class PostLoanChanges {
            private PostLoanChanges() {}
            @Schema(example = "[21, 22]")
            public List<Integer> removedEntityIds;
        }
        @Schema(example = "1")
        public Integer loanId;
        public PostLoanChanges changes;
    }
}
