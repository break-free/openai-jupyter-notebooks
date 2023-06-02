
package org.apache.fineract.portfolio.meeting.domain;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {
    Meeting findByCalendarInstanceIdAndMeetingDate(Long calendarInstanceId, LocalDate meetingDate);
}
