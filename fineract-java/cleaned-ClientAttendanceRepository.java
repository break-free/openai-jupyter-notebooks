
package org.apache.fineract.portfolio.meeting.attendance.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ClientAttendanceRepository extends JpaRepository<ClientAttendance, Long>, JpaSpecificationExecutor<ClientAttendance> {
}
