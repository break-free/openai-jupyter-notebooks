
package org.apache.fineract.infrastructure.reportmailingjob.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobConfigurationData;
import org.apache.fineract.infrastructure.reportmailingjob.exception.ReportMailingJobConfigurationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class ReportMailingJobConfigurationReadPlatformServiceImpl implements ReportMailingJobConfigurationReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ReportMailingJobConfigurationReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Collection<ReportMailingJobConfigurationData> retrieveAllReportMailingJobConfigurations() {
        final ReportMailingJobConfigurationMapper mapper = new ReportMailingJobConfigurationMapper();
        final String sql = "select " + mapper.reportMailingJobConfigurationSchema();
        return this.jdbcTemplate.query(sql, mapper); 
    }
    @Override
    public ReportMailingJobConfigurationData retrieveReportMailingJobConfiguration(String name) {
        try {
            final ReportMailingJobConfigurationMapper mapper = new ReportMailingJobConfigurationMapper();
            final String sql = "select " + mapper.reportMailingJobConfigurationSchema() + " where rmjc.name = ?";
            return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] { name }); 
        }
        catch (final EmptyResultDataAccessException ex) {
            throw new ReportMailingJobConfigurationNotFoundException(name, ex);
        }
    }
    private static final class ReportMailingJobConfigurationMapper implements RowMapper<ReportMailingJobConfigurationData> {
        public String reportMailingJobConfigurationSchema() {
            return "rmjc.id, rmjc.name, rmjc.value " + "from m_report_mailing_job_configuration rmjc";
        }
        @Override
        public ReportMailingJobConfigurationData mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Integer id = JdbcSupport.getInteger(rs, "id");
            final String name = rs.getString("name");
            final String value = rs.getString("value");
            return ReportMailingJobConfigurationData.newInstance(id, name, value);
        }
    }
}
