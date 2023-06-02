
package org.apache.fineract.portfolio.loanaccount.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
final class LoanChargesApiResourceSwagger {
    private LoanChargesApiResourceSwagger() {}
    @Schema(description = "GetLoansLoanIdChargesChargeIdResponse")
    public static final class GetLoansLoanIdChargesChargeIdResponse {
        private GetLoansLoanIdChargesChargeIdResponse() {}
        static final class GetLoanChargeTimeType {
            private GetLoanChargeTimeType() {}
            @Schema(example = "1")
            public Integer id;
            @Schema(example = "chargeTimeType.disbursement")
            public String code;
            @Schema(example = "Disbursement")
            public String description;
        }
        static final class GetLoanChargeCalculationType {
            private GetLoanChargeCalculationType() {}
            @Schema(example = "1")
            public Integer id;
            @Schema(example = "chargeCalculationType.flat")
            public String code;
            @Schema(example = "Flat")
            public String description;
        }
        static final class GetLoanChargeCurrency {
            private GetLoanChargeCurrency() {}
            @Schema(example = "USD")
            public String code;
            @Schema(example = "US Dollar")
            public String name;
            @Schema(example = "2")
            public Integer decimalPlaces;
            @Schema(example = "$")
            public String displaySymbol;
            @Schema(example = "currency.USD")
            public String nameCode;
            @Schema(example = "US Dollar ($)")
            public String displayLabel;
        }
        @Schema(example = "1")
        public Integer id;
        @Schema(example = "1")
        public Integer chargeId;
        @Schema(example = "Loan Processing fee")
        public String name;
        public GetLoanChargeTimeType chargeTimeType;
        public GetLoanChargeCalculationType chargeCalculationType;
        @Schema(example = "0")
        public Double percentage;
        @Schema(example = "0")
        public Double amountPercentageAppliedTo;
        public GetLoanChargeCurrency currency;
        @Schema(example = "100")
        public Float amount;
        @Schema(example = "0")
        public Float amountPaid;
        @Schema(example = "0")
        public Float amountWaived;
        @Schema(example = "0")
        public Float amountWrittenOff;
        @Schema(example = "100")
        public Float amountOutstanding;
        @Schema(example = "100")
        public Float amountOrPercentage;
        @Schema(example = "false")
        public Boolean penalty;
    }
    @Schema(description = "GetLoansLoanIdChargesTemplateResponse")
    public static final class GetLoansLoanIdChargesTemplateResponse {
        private GetLoansLoanIdChargesTemplateResponse() {}
        static final class GetLoanChargeTemplateChargeOptions {
            private GetLoanChargeTemplateChargeOptions() {}
            static final class GetLoanChargeTemplateChargeTimeType {
                private GetLoanChargeTemplateChargeTimeType() {}
                @Schema(example = "2")
                public Integer id;
                @Schema(example = "chargeTimeType.specifiedDueDate")
                public String code;
                @Schema(example = "Specified due date")
                public String description;
            }
            static final class GetLoanChargeTemplateChargeAppliesTo {
                private GetLoanChargeTemplateChargeAppliesTo() {}
                @Schema(example = "1  ")
                public Integer id;
                @Schema(example = "chargeAppliesTo.loan")
                public String code;
                @Schema(example = "Loan")
                public String description;
            }
            @Schema(example = "1")
            public Integer id;
            @Schema(example = "Collection fee")
            public String name;
            @Schema(example = "true")
            public Boolean active;
            @Schema(example = "false")
            public Boolean penalty;
            public GetLoansLoanIdChargesChargeIdResponse.GetLoanChargeCurrency currency;
            @Schema(example = "100")
            public Float amount;
            public GetLoanChargeTemplateChargeTimeType chargeTimeType;
            public GetLoanChargeTemplateChargeAppliesTo chargeAppliesTo;
            public GetLoansLoanIdChargesChargeIdResponse.GetLoanChargeCalculationType chargeCalculationType;
        }
        @Schema(example = "0")
        public Float amountPaid;
        @Schema(example = "0")
        public Float amountWaived;
        @Schema(example = "0")
        public Float amountWrittenOff;
        public Set<GetLoanChargeTemplateChargeOptions> chargeOptions;
        @Schema(example = "false")
        public Boolean penalty;
    }
    @Schema(description = " PostLoansLoanIdChargesRequest")
    public static final class PostLoansLoanIdChargesRequest {
        private PostLoansLoanIdChargesRequest() {}
        @Schema(example = "2")
        public Integer chargeId;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "100")
        public Float amount;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "29 April 2013")
        public String dueDate;
    }
    @Schema(description = " PostLoansLoanIdChargesResponse")
    public static final class PostLoansLoanIdChargesResponse {
        private PostLoansLoanIdChargesResponse() {}
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long clientId;
        @Schema(example = "1")
        public Long loanId;
        @Schema(example = "31")
        public Integer resourceId;
    }
    @Schema(description = " PutLoansLoanIdChargesChargeIdRequest")
    public static final class PutLoansLoanIdChargesChargeIdRequest {
        private PutLoansLoanIdChargesChargeIdRequest() {}
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "60")
        public Float amount;
        @Schema(example = "27 March 2013")
        public String dueDate;
    }
    @Schema(description = "PutLoansLoanIdChargesChargeIdResponse")
    public static final class PutLoansLoanIdChargesChargeIdResponse {
        private PutLoansLoanIdChargesChargeIdResponse() {}
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long clientId;
        @Schema(example = "1")
        public Long loanId;
        @Schema(example = "6")
        public Integer resourceId;
        public PutLoansLoanIdChargesChargeIdRequest changes;
    }
    @Schema(description = "PostLoansLoanIdChargesChargeIdRequest")
    public static final class PostLoansLoanIdChargesChargeIdRequest {
        private PostLoansLoanIdChargesChargeIdRequest() {}
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "19 September 2013")
        public String transactionDate;
    }
    @Schema(description = "PostLoansLoanIdChargesChargeIdResponse")
    public static final class PostLoansLoanIdChargesChargeIdResponse {
        private PostLoansLoanIdChargesChargeIdResponse() {}
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long clientId;
        @Schema(example = "6")
        public Long loanId;
        @Schema(example = "1")
        public Long savingsId;
        @Schema(example = "12")
        public Integer resourceId;
    }
    @Schema(description = "DeleteLoansLoanIdChargesChargeIdResponse")
    public static final class DeleteLoansLoanIdChargesChargeIdResponse {
        private DeleteLoansLoanIdChargesChargeIdResponse() {}
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long clientId;
        @Schema(example = "1")
        public Long loanId;
        @Schema(example = "2")
        public Integer resourceId;
    }
}
