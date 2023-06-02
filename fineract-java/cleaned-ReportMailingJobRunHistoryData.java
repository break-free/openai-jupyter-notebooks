
package org.apache.fineract.infrastructure.reportmailingjob.data;
import java.time.ZonedDateTime;
public final class ReportMailingJobRunHistoryData {
    private final Long id;
    private final Long reportMailingJobId;
    private final ZonedDateTime startDateTime;
    private final ZonedDateTime endDateTime;
    private final String status;
    private final String errorMessage;
    private final String errorLog;
    private ReportMailingJobRunHistoryData(Long id, Long reportMailingJobId, ZonedDateTime startDateTime, ZonedDateTime endDateTime,
            String status, String errorMessage, String errorLog) {
        this.id = id;
        this.reportMailingJobId = reportMailingJobId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.errorMessage = errorMessage;
        this.errorLog = errorLog;
    }
    public static ReportMailingJobRunHistoryData newInstance(Long id, Long reportMailingJobId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime, String status, String errorMessage, String errorLog) {
        return new ReportMailingJobRunHistoryData(id, reportMailingJobId, startDateTime, endDateTime, status, errorMessage, errorLog);
    }
    public Long getId() {
        return id;
    }
    public Long getReportMailingJobId() {
        return reportMailingJobId;
    }
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }
    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }
    public String getStatus() {
        return status;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public String getErrorLog() {
        return errorLog;
    }
}
