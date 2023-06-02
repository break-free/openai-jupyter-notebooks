
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ReportMailingJobRunHistoryRepository
        extends JpaRepository<ReportMailingJobRunHistory, Long>, JpaSpecificationExecutor<ReportMailingJobRunHistory> {
}
