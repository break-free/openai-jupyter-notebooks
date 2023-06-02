
package org.apache.fineract.portfolio.interestratechart.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
final class InterestRateChartsApiResourceSwagger {
    private InterestRateChartsApiResourceSwagger() {}
    @Schema(description = "GetInterestRateChartsTemplateResponse")
    public static final class GetInterestRateChartsTemplateResponse {
        private GetInterestRateChartsTemplateResponse() {}
        static final class GetInterestRateChartsTemplatePeriodTypes {
            private GetInterestRateChartsTemplatePeriodTypes() {}
            @Schema(example = "0")
            public Integer id;
            @Schema(example = "interestChartPeriodType.days")
            public String code;
            @Schema(example = "Days")
            public String description;
        }
        public Set<GetInterestRateChartsTemplatePeriodTypes> periodTypes;
    }
    @Schema(description = "GetInterestRateChartsResponse")
    public static final class GetInterestRateChartsResponse {
        private GetInterestRateChartsResponse() {}
        static final class GetInterestRateChartsChartSlabs {
            private GetInterestRateChartsChartSlabs() {}
            @Schema(example = "1")
            public Integer id;
            public GetInterestRateChartsTemplateResponse.GetInterestRateChartsTemplatePeriodTypes periodTypes;
            @Schema(example = "1")
            public Integer fromPeriod;
            @Schema(example = "6")
            public Integer annualInterestRate;
            public GetInterestRateChartsCurrency currency;
        }
        static final class GetInterestRateChartsCurrency {
            private GetInterestRateChartsCurrency() {}
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
        @Schema(example = "[2014, 1, 1]")
        public LocalDate fromDate;
        @Schema(example = "1")
        public Integer savingsProductId;
        @Schema(example = "Fixed Deposit Product 001")
        public String savingsProductName;
        public Set<GetInterestRateChartsChartSlabs> chartSlabs;
    }
    @Schema(description = "PostInterestRateChartsRequest")
    public static final class PostInterestRateChartsRequest {
        private PostInterestRateChartsRequest() {}
        @Schema(example = "Chart - 2014")
        public String name;
        @Schema(example = "This chart is applicable for year 2014")
        public String description;
        @Schema(example = "Document")
        public String type;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "01 Jan 2014")
        public String fromDate;
    }
    @Schema(description = "PostInterestRateChartsResponse")
    public static final class PostInterestRateChartsResponse {
        private PostInterestRateChartsResponse() {}
        @Schema(example = "1")
        public Integer resourceId;
    }
    @Schema(description = "PutInterestRateChartsChartIdRequest")
    public static final class PutInterestRateChartsChartIdRequest {
        private PutInterestRateChartsChartIdRequest() {}
        @Schema(example = "Interest rate chart for 2014")
        public String name;
        @Schema(example = "Interest rate chart for 2014")
        public String description;
    }
    @Schema(description = "PutInterestRateChartsChartIdResponse")
    public static final class PutInterestRateChartsChartIdResponse {
        private PutInterestRateChartsChartIdResponse() {}
        @Schema(example = "1")
        public Integer resourceId;
    }
    @Schema(description = "DeleteInterestRateChartsChartIdResponse")
    public static final class DeleteInterestRateChartsChartIdResponse {
        private DeleteInterestRateChartsChartIdResponse() {}
        @Schema(example = "1")
        public Integer resourceId;
    }
}
