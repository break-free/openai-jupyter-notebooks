
package org.apache.fineract.portfolio.self.runreport;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
final class SelfRunReportApiResourceSwagger {
    private SelfRunReportApiResourceSwagger() {}
    @Schema(description = "GetRunReportResponse")
    public static final class GetRunReportResponse {
        private GetRunReportResponse() {}
        static final class GetRunReportColumnHeaders {
            private GetRunReportColumnHeaders() {}
            @Schema(example = "Office/Branch")
            public String columnName;
            @Schema(example = "VARCHAR")
            public String columnType;
            @Schema(example = "false")
            public Boolean isColumnNullable;
            @Schema(example = "false")
            public Boolean isColumnPrimaryKey;
            @Schema(example = "[]")
            public String columnValues;
        }
        static final class GetPocketData {
            private GetPocketData() {}
            @Schema(example = "[\"Head Office\", \"000000001\", \"John Doe\"  \"2017-03-04\", \"786YYH7\"")
            public String row;
        }
        public Set<GetRunReportColumnHeaders> columnHeaders;
        public Set<GetPocketData> data;
    }
}
