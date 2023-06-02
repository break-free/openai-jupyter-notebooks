
package org.apache.fineract.infrastructure.creditbureau.data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.service.CreditReportReadPlatformService;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class CreditReportReadPlatformServiceImpl implements CreditReportReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    @Autowired
    public CreditReportReadPlatformServiceImpl(final PlatformSecurityContext context, final JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final class CreditReportDataMapper implements RowMapper<CreditReportData> {
        public String schema() {
            return " c.id as id, c.credit_bureau_id as creditBureauId , c.national_id as nationalId from m_creditreport c ";
        }
        @Override
        public CreditReportData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final Long creditBureauId = rs.getLong("creditBureauId");
            final String nationalId = rs.getString("nationalId");
            return CreditReportData.instance(id, creditBureauId, nationalId);
        }
    }
    @Override
    public Collection<CreditReportData> retrieveCreditReportDetails(Long creditBureauId) {
        this.context.authenticatedUser();
        final CreditReportDataMapper rm = new CreditReportDataMapper();
        final String sql = " select " + rm.schema() + " where c.creditBureauId = ? ";
        return this.jdbcTemplate.query(sql, rm, new Object[] { creditBureauId }); 
    }
}
