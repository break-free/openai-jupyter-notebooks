
package org.apache.fineract.infrastructure.dataqueries.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.apache.fineract.infrastructure.dataqueries.data.ResultsetColumnHeaderData;
import org.apache.fineract.infrastructure.dataqueries.data.ResultsetRowData;
final class RunreportsApiResourceSwagger {
    private RunreportsApiResourceSwagger() {}
    @Schema
    public static final class RunReportsResponse {
        private RunReportsResponse() {}
        public List<ResultsetColumnHeaderData> columnHeaders;
        public List<ResultsetRowData> data;
    }
}
