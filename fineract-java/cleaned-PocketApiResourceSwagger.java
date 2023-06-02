
package org.apache.fineract.portfolio.self.pockets.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
final class PocketApiResourceSwagger {
    private PocketApiResourceSwagger() {}
    @Schema(description = "PostLinkDelinkAccountsToFromPocketRequest")
    public static final class PostLinkDelinkAccountsToFromPocketRequest {
        private PostLinkDelinkAccountsToFromPocketRequest() {}
        static final class GetPocketAccountDetail {
            private GetPocketAccountDetail() {}
            @Schema(example = "11")
            public Integer accountId;
            @Schema(example = "LOAN")
            public String accountType;
        }
        public Set<GetPocketAccountDetail> accountDetail;
    }
    @Schema(description = "PostLinkDelinkAccountsToFromPocketResponse")
    public static final class PostLinkDelinkAccountsToFromPocketResponse {
        private PostLinkDelinkAccountsToFromPocketResponse() {}
        @Schema(example = "6")
        public Integer resourceId;
    }
    @Schema(description = "GetAccountsLinkedToPocketResponse")
    public static final class GetAccountsLinkedToPocketResponse {
        private GetAccountsLinkedToPocketResponse() {}
        static final class GetPocketLoanAccounts {
            private GetPocketLoanAccounts() {}
            @Schema(example = "6")
            public Integer pocketId;
            @Schema(example = "11")
            public Integer accountId;
            @Schema(example = "2")
            public Integer accountType;
            @Schema(example = "000000011")
            public Integer accountNumber;
            @Schema(example = "10")
            public Integer id;
        }
        static final class GetPocketSavingAccounts {
            private GetPocketSavingAccounts() {}
            @Schema(example = "6")
            public Integer pocketId;
            @Schema(example = "2")
            public Integer accountId;
            @Schema(example = "3")
            public Integer accountType;
            @Schema(example = "000000002")
            public Integer accountNumber;
            @Schema(example = "11")
            public Integer id;
        }
        static final class GetPocketShareAccounts {
            private GetPocketShareAccounts() {}
        }
        public Set<GetPocketLoanAccounts> loanAccounts;
        public Set<GetPocketSavingAccounts> savingAccounts;
        public Set<GetPocketShareAccounts> shareAccounts;
    }
}
