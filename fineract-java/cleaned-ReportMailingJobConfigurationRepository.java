
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ReportMailingJobConfigurationRepository
        extends JpaRepository<ReportMailingJobConfiguration, Integer>, JpaSpecificationExecutor<ReportMailingJobConfiguration> {
    ReportMailingJobConfiguration findByName(String name);
}
