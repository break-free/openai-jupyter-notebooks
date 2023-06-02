
package org.apache.fineract.infrastructure.jobs.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import org.apache.fineract.infrastructure.jobs.data.JobDetailHistoryData;
final class SchedulerJobApiResourceSwagger {
    private SchedulerJobApiResourceSwagger() {
    }
    @Schema(description = "GetJobsResponse")
    public static final class GetJobsResponse {
        private GetJobsResponse() {
        }
        @Schema(example = "1")
        public Long jobId;
        @Schema(example = "Update loan Summary")
        public String displayName;
        @Schema(example = "")
        public Date nextRunTime;
        @Schema(example = "")
        public String initializingError;
        @Schema(example = "0 0 22 1/1 * ? *")
        public String cronExpression;
        @Schema(example = "false")
        public boolean active;
        @Schema(example = "false")
        public boolean currentlyRunning;
        public JobDetailHistoryData lastRunHistory;
    }
    @Schema(description = "PutJobsJobsIDRequest")
    public static final class PutJobsJobIDRequest {
        private PutJobsJobIDRequest() {
        }
        @Schema(example = "Update loan Summary")
        public String displayName;
        @Schema(example = "0 0 22 1/1 * ? *")
        public String cronExpression;
        @Schema(example = "false")
        public boolean active;
    }
    @Schema(description = "GetJobsJobIDJobRunHistoryResponse")
    public static final class GetJobsJobIDJobRunHistoryResponse {
        private GetJobsJobIDJobRunHistoryResponse() {
        }
        static final class JobDetailHistoryDataSwagger {
            private JobDetailHistoryDataSwagger() {}
            @Schema(example = "1")
            public Long version;
            @Schema(example = "Jul 16, 2013 12:00:00 PM")
            public Date jobRunStartTime;
            @Schema(example = "Jul 16, 2013 12:00:00 PM")
            public Date jobRunEndTime;
            @Schema(example = "success")
            public String status;
            @Schema(example = "cron")
            public String triggerType;
        }
        @Schema(example = "8")
        public int totalFilteredRecords;
        public List<JobDetailHistoryDataSwagger> pageItems;
    }
}
