
package org.apache.fineract.organisation.provisioning.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.organisation.provisioning.data.ProvisioningCategoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class ProvisioningCategoryReadPlatformServiceImpl implements ProvisioningCategoryReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final ProvisioningCategoryRowMapper provisionCategoryRowMapper;
    @Autowired
    public ProvisioningCategoryReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.provisionCategoryRowMapper = new ProvisioningCategoryRowMapper();
    }
    @Override
    public Collection<ProvisioningCategoryData> retrieveAllProvisionCategories() {
        final String sql = "select " + this.provisionCategoryRowMapper.schema() + " from m_provision_category pc order by pc.id";
        return this.jdbcTemplate.query(sql, this.provisionCategoryRowMapper); 
    }
    private static final class ProvisioningCategoryRowMapper implements RowMapper<ProvisioningCategoryData> {
        @Override
        public ProvisioningCategoryData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = JdbcSupport.getLong(rs, "id");
            final String categoryName = rs.getString("category_name");
            final String description = rs.getString("description");
            return new ProvisioningCategoryData(id, categoryName, description);
        }
        public String schema() {
            return " pc.id as id, pc.category_name as category_name, pc.description as description";
        }
    }
}
