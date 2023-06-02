
package org.apache.fineract.infrastructure.creditbureau.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauData;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class CreditBureauReadPlatformServiceImpl implements CreditBureauReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    @Autowired
    public CreditBureauReadPlatformServiceImpl(final PlatformSecurityContext context, final JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final class CBMapper implements RowMapper<CreditBureauData> {
        public String schema() {
            return "cb.id as creditBureauID,cb.name as creditBureauName,cb.product as creditBureauProduct,"
                    + "cb.country as country,concat(cb.product,' - ',cb.name,' - ',cb.country) as cbSummary,cb.implementation_key as implementationKey from m_creditbureau cb";
        }
        @Override
        public CreditBureauData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final long id = rs.getLong("creditBureauID");
            final String name = rs.getString("creditBureauName");
            final String product = rs.getString("creditBureauProduct");
            final String country = rs.getString("country");
            final String cbSummary = rs.getString("cbSummary");
            final long implementationKey = rs.getLong("implementationKey");
            return CreditBureauData.instance(id, name, country, product, cbSummary, implementationKey);
        }
    }
    @Override
    public Collection<CreditBureauData> retrieveCreditBureau() {
        this.context.authenticatedUser();
        final CBMapper rm = new CBMapper();
        final String sql = "select " + rm.schema() + " order by id";
        return this.jdbcTemplate.query(sql, rm); 
    }
}
