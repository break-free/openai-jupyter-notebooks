
package org.apache.fineract.infrastructure.security.service;
import java.util.List;
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
public class JdbcTenantDetailsService implements TenantDetailsService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcTenantDetailsService(@Qualifier("hikariTenantDataSource") final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    @Cacheable(value = "tenantsById")
    public FineractPlatformTenant loadTenantById(final String tenantIdentifier) {
        try {
            final TenantMapper rm = new TenantMapper(false);
            final String sql = "select " + rm.schema() + " where t.identifier = ?";
            return this.jdbcTemplate.queryForObject(sql, rm, new Object[] { tenantIdentifier }); 
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidTenantIdentifierException("The tenant identifier: " + tenantIdentifier + " is not valid.", e);
        }
    }
    @Override
    public List<FineractPlatformTenant> findAllTenants() {
        final TenantMapper rm = new TenantMapper(false);
        final String sql = "select  " + rm.schema();
        final List<FineractPlatformTenant> fineractPlatformTenants = this.jdbcTemplate.query(sql, rm); 
        return fineractPlatformTenants;
    }
}
