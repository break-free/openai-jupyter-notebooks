
package org.apache.fineract.infrastructure.dataqueries.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ReportParameterUsageRepository
        extends JpaRepository<ReportParameterUsage, Long>, JpaSpecificationExecutor<ReportParameterUsage> {
    List<ReportParameterUsage> findByReport(Report report);
}
