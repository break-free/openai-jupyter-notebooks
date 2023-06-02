
package org.apache.fineract.infrastructure.reportmailingjob.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.infrastructure.dataqueries.data.ReportData;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobTimelineData;
final class ReportMailingJobApiResourceSwagger {
    private ReportMailingJobApiResourceSwagger() {
    }
    @Schema(description = "GetReportMailingJobsTemplate")
    public static final class GetReportMailingJobsTemplate {
        private GetReportMailingJobsTemplate() {
        }
        @Schema(example = "true")
        public boolean isActive;
        public List<EnumOptionData> emailAttachmentFileFormatOptions;
        public List<EnumOptionData> stretchyReportParamDateOptions;
    }
    @Schema(description = "GetReportMailingJobsResponse")
    public static final class GetReportMailingJobsResponse {
        private GetReportMailingJobsResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Client Numbers Report")
        public String name;
        @Schema(example = "Client Numbers Report")
        public String description;
        @Schema(example = "1469627093000")
        public ZonedDateTime startDateTime;
        @Schema(example = "")
        public String recurrence;
        public ReportMailingJobTimelineData timeline;
        @Schema(example = "info@musonisystem.com")
        public String emailRecipients;
        @Schema(example = "Client Numbers Report")
        public String emailSubject;
        @Schema(example = "Client Numbers Report")
        public String emailMessage;
        @Schema(example = "")
        public EnumOptionData emailAttachmentFileFormat;
        @Schema(example = "")
        public ReportData stretchyReport;
        @Schema(example = "{\"startDate\":\"2016-07-01\",\"endDate\":\"2016-08-02\",\"selectOffice\":\"1\",\"environementUrl\":\"environementUrl\"}")
        public String stretchyReportParamMap;
        @Schema(example = "1469627093000")
        public ZonedDateTime nextRunDateTime;
        @Schema(example = "0")
        public Integer numberOfRuns;
        @Schema(example = "true")
        public boolean isActive;
        @Schema(example = "1")
        public Long runAsUserId;
    }
    @Schema(description = "PostReportMailingJobsRequest")
    public static final class PostReportMailingJobsRequest {
        private PostReportMailingJobsRequest() {
        }
        @Schema(example = "en_GB")
        public String locale;
        @Schema(example = "dd-MM-yyyy HH:mm:ss")
        public String dateFormat;
        @Schema(example = "Client Numbers Report")
        public String name;
        @Schema(example = "Client Numbers Report")
        public String description;
        @Schema(example = "1469627093000")
        public ZonedDateTime startDateTime;
        @Schema(example = "120")
        public Long stretchyReportId;
        @Schema(example = "info@musonisystem.com")
        public String emailRecipients;
        @Schema(example = "Client Numbers Report")
        public String emailSubject;
        @Schema(example = "Client Numbers Report")
        public String emailMessage;
        @Schema(example = "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,WE,FR")
        public String recurrence;
        @Schema(example = "true")
        public boolean isActive;
        @Schema(example = "{\"startDate\":\"2016-07-01\",\"endDate\":\"2016-08-02\",\"selectOffice\":\"1\",\"environementUrl\":\"environementUrl\"}")
        public String stretchyReportParamMap;
    }
    @Schema(description = "PostReportMailingJobsResponse")
    public static final class PostReportMailingJobsResponse {
        private PostReportMailingJobsResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "PutReportMailingJobsRequest")
    public static final class PutReportMailingJobsRequest {
        private PutReportMailingJobsRequest() {
        }
        @Schema(example = "en_GB")
        public String locale;
        @Schema(example = "dd-MM-yyyy HH:mm:ss")
        public String dateFormat;
        @Schema(example = "10-08-2016 23:30:00")
        public ZonedDateTime startDateTime;
    }
    @Schema(description = "PutReportMailingJobsResponse")
    public static final class PutReportMailingJobsResponse {
        private PutReportMailingJobsResponse() {
        }
        static final class PutReportMailingJobsResponseChanges {
            private PutReportMailingJobsResponseChanges() {}
            @Schema(example = "10-08-2016 23:30:00")
            public ZonedDateTime startDateTime;
        }
        @Schema(example = "1")
        public Long resourceId;
        public PutReportMailingJobsResponseChanges changes;
    }
    @Schema(description = "DeleteReportMailingJobsRequest ")
    public static final class DeleteReportMailingJobsRequest {
        private DeleteReportMailingJobsRequest() {
        }
    }
    @Schema(description = "DeleteReportMailingJobsResponse")
    public static final class DeleteReportMailingJobsResponse {
        private DeleteReportMailingJobsResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
