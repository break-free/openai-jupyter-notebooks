
package org.apache.fineract.portfolio.meeting.domain;
import org.apache.fineract.portfolio.meeting.exception.MeetingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MeetingRepositoryWrapper {
    private final MeetingRepository repository;
    @Autowired
    public MeetingRepositoryWrapper(final MeetingRepository repository) {
        this.repository = repository;
    }
    public Meeting findOneWithNotFoundDetection(final Long meetingId) {
        return this.repository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException(meetingId));
    }
    public void save(final Meeting meeting) {
        this.repository.save(meeting);
    }
    public void delete(final Meeting meeting) {
        this.repository.delete(meeting);
    }
    public void saveAndFlush(final Meeting meeting) {
        this.repository.saveAndFlush(meeting);
    }
}
