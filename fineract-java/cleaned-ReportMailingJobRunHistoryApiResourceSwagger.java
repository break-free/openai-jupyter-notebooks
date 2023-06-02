
package org.apache.fineract.infrastructure.reportmailingjob.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
final class ReportMailingJobRunHistoryApiResourceSwagger {
    private ReportMailingJobRunHistoryApiResourceSwagger() {
    }
    @Schema(description = "GetReportMailingJobRunHistoryResponse")
    public static final class GetReportMailingJobRunHistoryResponse {
        private GetReportMailingJobRunHistoryResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "1")
        public Long reportMailingJobId;
        @Schema(example = "1469627093050")
        public ZonedDateTime startDateTime;
        @Schema(example = "1469627093050")
        public ZonedDateTime endDateTime;
        @Schema(example = "success")
        public String status;
        @Schema(example = "")
        public String errorMessage;
        @Schema(example = "")
        public String errorLog;
    }
}
