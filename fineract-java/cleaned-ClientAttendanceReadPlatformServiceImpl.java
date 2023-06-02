
package org.apache.fineract.portfolio.meeting.attendance.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.meeting.attendance.data.ClientAttendanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class ClientAttendanceReadPlatformServiceImpl implements ClientAttendanceReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ClientAttendanceReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final class ClientAttendanceDataMapper implements RowMapper<ClientAttendanceData> {
        public String schema() {
            return " select ca.id as id, ca.client_id as clientId, ca.attendance_type_enum as attendanceTypeId, "
                    + " c.display_name as clientName from m_meeting m inner join m_client_attendance ca on m.id = ca.meeting_id "
                    + " inner join m_client c on ca.client_id=c.id ";
        }
        @Override
        public ClientAttendanceData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final Long clientId = rs.getLong("clientId");
            final Integer attendanceTypeId = rs.getInt("attendanceTypeId");
            final String clientName = rs.getString("clientName");
            final EnumOptionData attendanceType = AttendanceEnumerations.attendanceType(attendanceTypeId);
            return ClientAttendanceData.instance(id, clientId, clientName, attendanceType);
        }
    }
    @Override
    public Collection<ClientAttendanceData> retrieveClientAttendanceByMeetingId(final Long meetingId) {
        final ClientAttendanceDataMapper rm = new ClientAttendanceDataMapper();
        final String sql = rm.schema() + " where m.id = ? ";
        return this.jdbcTemplate.query(sql, rm, new Object[] { meetingId }); 
    }
}
