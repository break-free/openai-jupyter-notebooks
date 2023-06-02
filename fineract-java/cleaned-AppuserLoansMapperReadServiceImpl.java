
package org.apache.fineract.portfolio.self.loanaccount.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class AppuserLoansMapperReadServiceImpl implements AppuserLoansMapperReadService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public AppuserLoansMapperReadServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Boolean isLoanMappedToUser(Long loanId, Long appUserId) {
        return this.jdbcTemplate.queryForObject(
                "select case when (count(*) > 0) then true else false end " + " from m_selfservice_user_client_mapping as m "
                        + " left join m_loan as l on l.client_id = m.client_id " + " where l.id = ? and m.appuser_id = ? ",
                Boolean.class, loanId, appUserId);
    }
}
