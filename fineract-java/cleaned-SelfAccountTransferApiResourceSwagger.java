
package org.apache.fineract.portfolio.self.account.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
final class SelfAccountTransferApiResourceSwagger {
    private SelfAccountTransferApiResourceSwagger() {}
    @Schema(description = "GetAccountTransferTemplateResponse")
    public static final class GetAccountTransferTemplateResponse {
        private GetAccountTransferTemplateResponse() {}
        static final class GetAccountOptions {
            private GetAccountOptions() {}
            @Schema(example = "2")
            public Integer id;
            @Schema(example = "accountType.savings")
            public String code;
            @Schema(example = "Savings Account")
            public String description;
        }
        public Set<GetAccountOptions> accountTypeOptions;
        static final class GetFromAccountOptions {
            private GetFromAccountOptions() {}
            @Schema(example = "2")
            public Integer accountId;
            @Schema(example = "00000001")
            public Integer accountNo;
            public GetAccountTransferTemplateResponse.GetAccountOptions accountType;
            @Schema(example = "1")
            public Integer clientId;
            @Schema(example = "ABC")
            public String clientName;
            @Schema(example = "1")
            public Integer officeId;
            @Schema(example = "HEAD OFFICE")
            public String officeName;
        }
        public Set<GetFromAccountOptions> fromAccountTypeOptions;
        static final class GetToAccountOptions {
            private GetToAccountOptions() {}
            @Schema(example = "2")
            public Integer accountId;
            @Schema(example = "00000001")
            public Integer accountNo;
            public GetAccountTransferTemplateResponse.GetAccountOptions accountType;
            @Schema(example = "1")
            public Integer clientId;
            @Schema(example = "ABC")
            public String clientName;
            @Schema(example = "1")
            public Integer officeId;
            @Schema(example = "HEAD OFFICE")
            public String officeName;
        }
        public Set<GetFromAccountOptions> toAccountTypeOptions;
    }
    @Schema(description = "PostNewTransferRequest")
    public static final class PostNewTransferRequest {
        private PostNewTransferRequest() {}
        @Schema(example = "1")
        public Integer fromOfficeId;
        @Schema(example = "1")
        public Integer fromClientId;
        @Schema(example = "2")
        public Integer fromAccountType;
        @Schema(example = "1")
        public Integer fromAccountId;
        @Schema(example = "1")
        public Integer toOfficeId;
        @Schema(example = "1")
        public Integer toClientId;
        @Schema(example = "2")
        public Integer toAccountType;
        @Schema(example = "2")
        public Integer toAccountId;
        @Schema(example = "dd  MMMM YYYY")
        public String dateFormat;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "01  August 2011")
        public String transferDate;
        @Schema(example = "112.45")
        public Float transferAmount;
        @Schema(example = "A description of the transfer")
        public String transferDescription;
    }
    @Schema(description = "PostNewTransferResponse")
    public static final class PostNewTransferResponse {
        private PostNewTransferResponse() {}
        @Schema(example = "1")
        public Integer savingsId;
        @Schema(example = "1")
        public Integer resourceId;
    }
}
