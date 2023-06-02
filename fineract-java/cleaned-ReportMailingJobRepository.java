
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ReportMailingJobRepository extends JpaRepository<ReportMailingJob, Long>, JpaSpecificationExecutor<ReportMailingJob> {
    Collection<ReportMailingJob> findByIsActiveTrueAndIsDeletedFalse();
}
