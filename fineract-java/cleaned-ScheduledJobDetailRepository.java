
package org.apache.fineract.infrastructure.jobs.domain;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ScheduledJobDetailRepository
        extends JpaRepository<ScheduledJobDetail, Long>, JpaSpecificationExecutor<ScheduledJobDetail> {
    @Query("select jobDetail from ScheduledJobDetail jobDetail where jobDetail.jobKey = :jobKey")
    ScheduledJobDetail findByJobKey(@Param("jobKey") String jobKey);
    @Query("select jobDetail from ScheduledJobDetail jobDetail where jobDetail.id=:jobId")
    ScheduledJobDetail findByJobId(@Param("jobId") Long jobId);
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select jobDetail from ScheduledJobDetail jobDetail where jobDetail.jobKey = :jobKey")
    ScheduledJobDetail findByJobKeyWithLock(@Param("jobKey") String jobKey);
    @Query("select jobDetail from ScheduledJobDetail jobDetail where jobDetail.isMismatchedJob = :isMismatchedJob")
    List<ScheduledJobDetail> findAllMismatchedJobs(@Param("isMismatchedJob") boolean isMismatchedJob);
    @Query("select jobDetail from ScheduledJobDetail jobDetail where jobDetail.nodeId = :nodeId or jobDetail.nodeId = 0")
    List<ScheduledJobDetail> findAllJobs(@Param("nodeId") Integer nodeId);
}
