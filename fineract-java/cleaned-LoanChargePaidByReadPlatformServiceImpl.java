
package org.apache.fineract.portfolio.loanaccount.service;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.loanaccount.data.LoanChargePaidByData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class LoanChargePaidByReadPlatformServiceImpl implements LoanChargePaidByReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    @Autowired
    public LoanChargePaidByReadPlatformServiceImpl(final PlatformSecurityContext context, final JdbcTemplate jdbcTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<LoanChargePaidByData> getLoanChargesPaidByTransactionId(Long transactionId) {
        this.context.authenticatedUser();
        final LoanChargePaidByMapper rm = new LoanChargePaidByMapper();
        final String sql = "select " + rm.loanChargePaidBySchema() + " where lcpd.loan_transaction_id = ?";
        return this.jdbcTemplate.query(sql, rm, transactionId); 
    }
    private static final class LoanChargePaidByMapper implements RowMapper<LoanChargePaidByData> {
        public String loanChargePaidBySchema() {
            return "lcpd.id as id, lcpd.amount as amount, lcpd.installment_number as installmentNumber,"
                    + " lcpd.loan_charge_id as chargeId, lcpd.loan_transaction_id as transactionId, " + " c.name as chargeName"
                    + " from m_loan_charge_paid_by lcpd" + " join m_loan_charge lc on lc.id=lcpd.loan_charge_Id"
                    + " join m_charge c on c.id=lc.charge_id";
        }
        @Override
        public LoanChargePaidByData mapRow(ResultSet rs, @SuppressWarnings("unused") int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final BigDecimal amount = rs.getBigDecimal("amount");
            final Integer installmentNumber = rs.getInt("installmentNumber");
            final Long chargeId = rs.getLong("chargeId");
            final Long transactionId = rs.getLong("transactionId");
            final String chargeName = rs.getString("chargeName");
            return new LoanChargePaidByData(id, amount, installmentNumber, chargeId, transactionId, chargeName);
        }
    }
}
