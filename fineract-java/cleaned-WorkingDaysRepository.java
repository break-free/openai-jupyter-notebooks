
package org.apache.fineract.organisation.workingdays.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface WorkingDaysRepository extends JpaRepository<WorkingDays, Long>, JpaSpecificationExecutor<WorkingDays> {
}
