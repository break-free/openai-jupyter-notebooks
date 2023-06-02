
package org.apache.fineract.portfolio.self.savings.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class AppuserSavingsMapperReadServiceImpl implements AppuserSavingsMapperReadService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public AppuserSavingsMapperReadServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Boolean isSavingsMappedToUser(Long savingsId, Long appUserId) {
        return this.jdbcTemplate.queryForObject(
                "select case when (count(*) > 0) then true else false end " + " from m_selfservice_user_client_mapping as m "
                        + " left join m_savings_account as s on s.client_id = m.client_id " + " where s.id = ? and m.appuser_id = ? ",
                Boolean.class, savingsId, appUserId);
    }
}
