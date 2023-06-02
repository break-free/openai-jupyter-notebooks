
package org.apache.fineract.infrastructure.jobs.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface JobParameterRepository extends JpaRepository<JobParameter, Long>, JpaSpecificationExecutor<JobParameter> {
    @Query("select jobParameter from JobParameter jobParameter where jobParameter.jobId=:jobId")
    List<JobParameter> findJobParametersByJobId(@Param("jobId") Long jobId);
}
