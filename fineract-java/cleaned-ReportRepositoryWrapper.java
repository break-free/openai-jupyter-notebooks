
package org.apache.fineract.infrastructure.dataqueries.domain;
import org.apache.fineract.infrastructure.dataqueries.exception.ReportNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReportRepositoryWrapper {
    private final ReportRepository reportRepository;
    @Autowired
    public ReportRepositoryWrapper(final ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    public Report findOneThrowExceptionIfNotFound(final Long id) {
        return this.reportRepository.findById(id).orElseThrow(() -> new ReportNotFoundException(id));
    }
}
