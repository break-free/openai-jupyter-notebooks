
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import org.apache.fineract.infrastructure.reportmailingjob.exception.ReportMailingJobNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReportMailingJobRepositoryWrapper {
    private final ReportMailingJobRepository reportMailingJobRepository;
    @Autowired
    public ReportMailingJobRepositoryWrapper(final ReportMailingJobRepository reportMailingJobRepository) {
        this.reportMailingJobRepository = reportMailingJobRepository;
    }
    public ReportMailingJob findOneThrowExceptionIfNotFound(final Long id) {
        final ReportMailingJob reportMailingJob = this.reportMailingJobRepository.findById(id)
                .orElseThrow(() -> new ReportMailingJobNotFoundException(id));
        if (reportMailingJob.isDeleted()) {
            throw new ReportMailingJobNotFoundException(id);
        }
        return reportMailingJob;
    }
    public ReportMailingJobRepository getReportMailingJobRepository() {
        return this.reportMailingJobRepository;
    }
}
