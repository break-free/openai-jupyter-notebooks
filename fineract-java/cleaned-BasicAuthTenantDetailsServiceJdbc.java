
package org.apache.fineract.infrastructure.security.service;
import javax.sql.DataSource;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.security.exception.InvalidTenantIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class BasicAuthTenantDetailsServiceJdbc implements BasicAuthTenantDetailsService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BasicAuthTenantDetailsServiceJdbc(@Qualifier("hikariTenantDataSource") final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    @Cacheable(value = "tenantsById")
    public FineractPlatformTenant loadTenantById(final String tenantIdentifier, final boolean isReport) {
        try {
            final TenantMapper rm = new TenantMapper(isReport);
            final String sql = "select  " + rm.schema() + " where t.identifier = ?";
            return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { tenantIdentifier }); 
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidTenantIdentifierException("The tenant identifier: " + tenantIdentifier + " is not valid.", e);
        }
    }
}
