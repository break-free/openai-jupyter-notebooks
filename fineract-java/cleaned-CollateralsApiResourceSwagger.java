
package org.apache.fineract.portfolio.collateral.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
final class CollateralsApiResourceSwagger {
    private CollateralsApiResourceSwagger() {}
    @Schema(description = "GetLoansLoanIdCollateralsResponse")
    public static final class GetLoansLoanIdCollateralsResponse {
        private GetLoansLoanIdCollateralsResponse() {}
        static final class GetCollateralTypeResponse {
            private GetCollateralTypeResponse() {}
            @Schema(example = "8")
            public Integer id;
            @Schema(example = "Gold")
            public String name;
        }
        static final class GetCollateralCurrencyResponse {
            private GetCollateralCurrencyResponse() {}
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
        @Schema(example = "12")
        public Integer id;
        public GetCollateralTypeResponse type;
        @Schema(example = "50000")
        public Long value;
        @Schema(example = "24 Carat Gold chain weighing 12 grams")
        public String description;
        public GetCollateralCurrencyResponse currency;
    }
    @Schema(description = "PostLoansLoanIdCollateralsRequest")
    public static final class PostLoansLoanIdCollateralsRequest {
        private PostLoansLoanIdCollateralsRequest() {}
        @Schema(example = "9")
        public Integer collateralTypeId;
    }
    @Schema(description = "PostLoansLoanIdCollateralsResponse")
    public static final class PostLoansLoanIdCollateralsResponse {
        private PostLoansLoanIdCollateralsResponse() {}
        @Schema(example = "12")
        public Integer resourceId;
    }
    @Schema(description = "PutLoansLoandIdCollateralsCollateralIdRequest")
    public static final class PutLoansLoandIdCollateralsCollateralIdRequest {
        private PutLoansLoandIdCollateralsCollateralIdRequest() {}
        @Schema(example = "22 Carat Gold chain weighing 12 grams")
        public String description;
    }
    @Schema(description = "PutLoansLoanIdCollateralsCollateralIdResponse")
    public static final class PutLoansLoanIdCollateralsCollateralIdResponse {
        private PutLoansLoanIdCollateralsCollateralIdResponse() {}
        @Schema(example = "1")
        public Integer loanId;
        @Schema(example = "12")
        public Integer resourceId;
        public PutLoansLoandIdCollateralsCollateralIdRequest changes;
    }
    @Schema(description = "GetLoansLoanIdCollateralsTemplateResponse")
    public static final class GetLoansLoanIdCollateralsTemplateResponse {
        private GetLoansLoanIdCollateralsTemplateResponse() {}
        static final class GetCollateralsTemplateAllowedTypes {
            private GetCollateralsTemplateAllowedTypes() {}
            @Schema(example = "9")
            public Integer id;
            @Schema(example = "Silver")
            public String name;
            @Schema(example = "0")
            public Integer position;
        }
        public Set<GetCollateralsTemplateAllowedTypes> allowedCollateralTypes;
    }
    @Schema(description = "DeleteLoansLoanIdCollateralsCollateralIdResponse")
    public static final class DeleteLoansLoanIdCollateralsCollateralIdResponse {
        private DeleteLoansLoanIdCollateralsCollateralIdResponse() {}
        @Schema(example = "1")
        public Integer loanId;
        @Schema(example = "12")
        public Integer resourceId;
    }
}
