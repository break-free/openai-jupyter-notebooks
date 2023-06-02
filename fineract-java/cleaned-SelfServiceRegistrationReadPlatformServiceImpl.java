
package org.apache.fineract.portfolio.self.registration.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class SelfServiceRegistrationReadPlatformServiceImpl implements SelfServiceRegistrationReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SelfServiceRegistrationReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean isClientExist(String accountNumber, String firstName, String lastName, String mobileNumber,
            boolean isEmailAuthenticationMode) {
        String sql = "select count(*) from m_client where account_no = ? and firstname = ? and lastname = ?";
        Object[] params = new Object[] { accountNumber, firstName, lastName };
        if (!isEmailAuthenticationMode) {
            sql = sql + " and mobile_no = ?";
            params = new Object[] { accountNumber, firstName, lastName, mobileNumber };
        }
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, params);
        if (count == 0) {
            return false;
        }
        return true;
    }
}
