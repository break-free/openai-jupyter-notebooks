
package org.apache.fineract.portfolio.account.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
final class StandingInstructionHistoryApiResourceSwagger {
    private StandingInstructionHistoryApiResourceSwagger() {}
    @Schema(description = "GetStandingInstructionRunHistoryResponse")
    public static final class GetStandingInstructionRunHistoryResponse {
        private GetStandingInstructionRunHistoryResponse() {}
        static final class GetStandingInstructionHistoryPageItemsResponse {
            static final class GetStandingInstructionHistoryPageItemsFromClient {
                private GetStandingInstructionHistoryPageItemsFromClient() {}
                @Schema(example = "1")
                public Long id;
                @Schema(example = "Test client")
                public String displayName;
                @Schema(example = "1")
                public Long officeId;
                @Schema(example = "Head Office")
                public String officeName;
            }
            static final class GetStandingInstructionHistoryFromAccount {
                private GetStandingInstructionHistoryFromAccount() {}
                @Schema(example = "2")
                public Long id;
                @Schema(example = "000000002")
                public Long accountNo;
                @Schema(example = "1")
                public Long productId;
                @Schema(example = "General Savings")
                public String productName;
            }
            static final class GetStandingInstructionHistoryToAccount {
                private GetStandingInstructionHistoryToAccount() {}
                @Schema(example = "1")
                public Long id;
                @Schema(example = "000000001")
                public Long accountNo;
                @Schema(example = "1")
                public Long productId;
                @Schema(example = "General Savings")
                public String productName;
            }
            static final class GetStandingInstructionHistoryToClient {
                private GetStandingInstructionHistoryToClient() {}
                @Schema(example = "1")
                public Long id;
                @Schema(example = "Test client")
                public String displayName;
                @Schema(example = "1")
                public Long officeId;
                @Schema(example = "Head Office")
                public String officeName;
            }
            @Schema(example = "1")
            public Long standingInstructionId;
            @Schema(example = "ACC Transfer")
            public String name;
            public StandingInstructionApiResourceSwagger.GetStandingInstructionsResponse.GetPageItemsStandingInstructionSwagger.GetFromOfficeStandingInstructionSwagger fromOffice;
            public GetStandingInstructionHistoryPageItemsFromClient fromClient;
            public StandingInstructionApiResourceSwagger.GetStandingInstructionsResponse.GetPageItemsStandingInstructionSwagger.GetFromAccountTypeStandingInstructionSwagger fromAccountType;
            public GetStandingInstructionHistoryFromAccount fromAccount;
            public StandingInstructionApiResourceSwagger.GetStandingInstructionsResponse.GetPageItemsStandingInstructionSwagger.GetToAccountTypeStandingInstructionSwagger toAccountType;
            public GetStandingInstructionHistoryToAccount toAccount;
            public StandingInstructionApiResourceSwagger.GetStandingInstructionsResponse.GetPageItemsStandingInstructionSwagger.GetToOfficeStandingInstructionSwagger toOffice;
            public GetStandingInstructionHistoryToClient toClient;
            @Schema(example = "10")
            public Float amount;
            @Schema(example = "success")
            public String status;
            @Schema(example = "[2014, 6, 30]")
            public LocalDate executionTime;
            @Schema(example = " ")
            public String errorLog;
        }
        @Schema(example = "2")
        public Integer totalFilteredRecords;
        public Set<GetStandingInstructionHistoryPageItemsResponse> pageItems;
    }
}
