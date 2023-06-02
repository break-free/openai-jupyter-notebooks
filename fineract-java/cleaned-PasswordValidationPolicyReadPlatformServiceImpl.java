
package org.apache.fineract.useradministration.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.database.DatabaseSpecificSQLGenerator;
import org.apache.fineract.useradministration.data.PasswordValidationPolicyData;
import org.apache.fineract.useradministration.exception.PasswordValidationPolicyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class PasswordValidationPolicyReadPlatformServiceImpl implements PasswordValidationPolicyReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseSpecificSQLGenerator sqlGenerator;
    private final PasswordValidationPolicyMapper passwordValidationPolicyMapper;
    @Autowired
    public PasswordValidationPolicyReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate, DatabaseSpecificSQLGenerator sqlGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlGenerator = sqlGenerator;
        this.passwordValidationPolicyMapper = new PasswordValidationPolicyMapper(sqlGenerator);
    }
    @Override
    public Collection<PasswordValidationPolicyData> retrieveAll() {
        final String sql = "select " + this.passwordValidationPolicyMapper.schema() + " order by pvp.id";
        return this.jdbcTemplate.query(sql, this.passwordValidationPolicyMapper); 
    }
    @Override
    public PasswordValidationPolicyData retrieveActiveValidationPolicy() {
        try {
            final String sql = "select " + this.passwordValidationPolicyMapper.schema() + " where pvp.active = true";
            return this.jdbcTemplate.queryForObject(sql, this.passwordValidationPolicyMapper); 
        } catch (final EmptyResultDataAccessException e) {
            throw new PasswordValidationPolicyNotFoundException(e);
        }
    }
    protected static final class PasswordValidationPolicyMapper implements RowMapper<PasswordValidationPolicyData> {
        private final DatabaseSpecificSQLGenerator sqlGenerator;
        public PasswordValidationPolicyMapper(DatabaseSpecificSQLGenerator sqlGenerator) {
            this.sqlGenerator = sqlGenerator;
        }
        @Override
        public PasswordValidationPolicyData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = JdbcSupport.getLong(rs, "id");
            final Boolean active = rs.getBoolean("active");
            final String description = rs.getString("description");
            final String key = rs.getString("key");
            return new PasswordValidationPolicyData(id, active, description, key);
        }
        public String schema() {
            return " pvp.id as id, pvp.active as active, pvp.description as description, pvp." + sqlGenerator.escape("key") + " as "
                    + sqlGenerator.escape("key") + "" + " from m_password_validation_policy pvp";
        }
    }
}
