
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailConfigurationData;
import org.apache.fineract.infrastructure.campaigns.email.exception.EmailConfigurationNotFoundException;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class EmailConfigurationReadPlatformServiceImpl implements EmailConfigurationReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final EmailConfigurationRowMapper emailConfigurationRowMapper;
    @Autowired
    public EmailConfigurationReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.emailConfigurationRowMapper = new EmailConfigurationRowMapper();
    }
    private static final class EmailConfigurationRowMapper implements RowMapper<EmailConfigurationData> {
        final String schema;
        EmailConfigurationRowMapper() {
            final StringBuilder sql = new StringBuilder(300);
            sql.append("cnf.id as id, ");
            sql.append("cnf.name as name, ");
            sql.append("cnf.value as value ");
            sql.append("from scheduled_email_configuration cnf");
            this.schema = sql.toString();
        }
        public String schema() {
            return this.schema;
        }
        @Override
        public EmailConfigurationData mapRow(ResultSet rs, @SuppressWarnings("unused") int rowNum) throws SQLException {
            final Long id = JdbcSupport.getLong(rs, "id");
            final String name = rs.getString("name");
            final String value = rs.getString("value");
            return EmailConfigurationData.instance(id, name, value);
        }
    }
    @Override
    public Collection<EmailConfigurationData> retrieveAll() {
        final String sql = "select " + this.emailConfigurationRowMapper.schema();
        return this.jdbcTemplate.query(sql, this.emailConfigurationRowMapper); 
    }
    @Override
    public EmailConfigurationData retrieveOne(String name) {
        try {
            final String sql = "select " + this.emailConfigurationRowMapper.schema() + " where cnf.name = ?";
            return this.jdbcTemplate.queryForObject(sql, this.emailConfigurationRowMapper, new Object[] { name }); 
        }
        catch (final EmptyResultDataAccessException e) {
            throw new EmailConfigurationNotFoundException(name, e);
        }
    }
}
