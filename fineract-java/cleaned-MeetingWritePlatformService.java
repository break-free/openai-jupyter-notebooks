
package org.apache.fineract.portfolio.meeting.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface MeetingWritePlatformService {
    CommandProcessingResult createMeeting(JsonCommand command);
    void updateCollectionSheetAttendance(JsonCommand command);
    CommandProcessingResult updateMeeting(JsonCommand command);
    CommandProcessingResult deleteMeeting(Long meetingId);
    CommandProcessingResult saveOrUpdateAttendance(JsonCommand command);
}
