
package org.apache.fineract.portfolio.self.shareaccounts.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class AppUserShareAccountsMapperReadPlatformServiceImpl implements AppUserShareAccountsMapperReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public AppUserShareAccountsMapperReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Boolean isShareAccountsMappedToUser(Long accountId, Long appUserId) {
        return this.jdbcTemplate.queryForObject("select case when (count(*) > 0) then true else false end "
                + " from m_selfservice_user_client_mapping as m "
                + " left join m_share_account as shares on shares.client_id = m.client_id  " + " where shares.id = ? and m.appuser_id = ? ",
                Boolean.class, accountId, appUserId);
    }
}
