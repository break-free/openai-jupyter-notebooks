
package org.apache.fineract.portfolio.paymenttype.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class PaymentTypeReadPlatformServiceImpl implements PaymentTypeReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    @Autowired
    public PaymentTypeReadPlatformServiceImpl(final PlatformSecurityContext context, final JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Collection<PaymentTypeData> retrieveAllPaymentTypes() {
        this.context.authenticatedUser();
        final PaymentTypeMapper ptm = new PaymentTypeMapper();
        final String sql = "select " + ptm.schema() + "order by position";
        return this.jdbcTemplate.query(sql, ptm); 
    }
    @Override
    public PaymentTypeData retrieveOne(Long paymentTypeId) {
        this.context.authenticatedUser();
        final PaymentTypeMapper ptm = new PaymentTypeMapper();
        final String sql = "select " + ptm.schema() + "where pt.id = ?";
        return this.jdbcTemplate.queryForObject(sql, ptm, new Object[] { paymentTypeId }); 
    }
    private static final class PaymentTypeMapper implements RowMapper<PaymentTypeData> {
        public String schema() {
            return " pt.id as id, pt.value as name, pt.description as description,pt.is_cash_payment as isCashPayment,pt.order_position as position from m_payment_type pt ";
        }
        @Override
        public PaymentTypeData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String name = rs.getString("name");
            final String description = rs.getString("description");
            final boolean isCashPayment = rs.getBoolean("isCashPayment");
            final Long position = rs.getLong("position");
            return PaymentTypeData.instance(id, name, description, isCashPayment, position);
        }
    }
}
