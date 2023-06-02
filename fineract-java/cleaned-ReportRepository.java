
package org.apache.fineract.infrastructure.dataqueries.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
}
