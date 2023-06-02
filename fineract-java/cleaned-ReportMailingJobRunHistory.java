
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_report_mailing_job_run_history")
public class ReportMailingJobRunHistory extends AbstractPersistableCustom {
    private static final long serialVersionUID = -3757370929988421076L;
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private ReportMailingJob reportMailingJob;
    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "error_message", nullable = false)
    private String errorMessage;
    @Column(name = "error_log", nullable = false)
    private String errorLog;
    protected ReportMailingJobRunHistory() {}
    private ReportMailingJobRunHistory(final ReportMailingJob reportMailingJob, final LocalDateTime startDateTime,
            final LocalDateTime endDateTime, final String status, final String errorMessage, final String errorLog) {
        this.reportMailingJob = reportMailingJob;
        this.startDateTime = null;
        if (startDateTime != null) {
            this.startDateTime = startDateTime;
        }
        this.endDateTime = null;
        if (endDateTime != null) {
            this.endDateTime = endDateTime;
        }
        this.status = status;
        this.errorMessage = errorMessage;
        this.errorLog = errorLog;
    }
    public static ReportMailingJobRunHistory newInstance(final ReportMailingJob reportMailingJob, final LocalDateTime startDateTime,
            final LocalDateTime endDateTime, final String status, final String errorMessage, final String errorLog) {
        return new ReportMailingJobRunHistory(reportMailingJob, startDateTime, endDateTime, status, errorMessage, errorLog);
    }
    public ReportMailingJob getReportMailingJob() {
        return this.reportMailingJob;
    }
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
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
