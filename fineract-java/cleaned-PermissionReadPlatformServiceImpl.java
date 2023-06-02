
package org.apache.fineract.useradministration.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.database.DatabaseSpecificSQLGenerator;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.useradministration.data.PermissionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class PermissionReadPlatformServiceImpl implements PermissionReadPlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionReadPlatformServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseSpecificSQLGenerator sqlGenerator;
    private final PlatformSecurityContext context;
    @Autowired
    public PermissionReadPlatformServiceImpl(final PlatformSecurityContext context, final JdbcTemplate jdbcTemplate,
            DatabaseSpecificSQLGenerator sqlGenerator) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
        this.sqlGenerator = sqlGenerator;
    }
    @Override
    public Collection<PermissionData> retrieveAllPermissions() {
        this.context.authenticatedUser();
        final PermissionUsageDataMapper mapper = new PermissionUsageDataMapper(sqlGenerator);
        final String sql = mapper.permissionSchema();
        LOG.info("retrieveAllPermissions: {}", sql);
        return this.jdbcTemplate.query(sql, mapper, new Object[] {});
    }
    @Override
    public Collection<PermissionData> retrieveAllMakerCheckerablePermissions() {
        this.context.authenticatedUser();
        final PermissionUsageDataMapper mapper = new PermissionUsageDataMapper(sqlGenerator);
        final String sql = mapper.makerCheckerablePermissionSchema();
        LOG.info("retrieveAllMakerCheckerablePermissions: {}", sql);
        return this.jdbcTemplate.query(sql, mapper, new Object[] {});
    }
    @Override
    public Collection<PermissionData> retrieveAllRolePermissions(final Long roleId) {
        final PermissionUsageDataMapper mapper = new PermissionUsageDataMapper(sqlGenerator);
        final String sql = mapper.rolePermissionSchema();
        LOG.info("retrieveAllRolePermissions: {}", sql);
        return this.jdbcTemplate.query(sql, mapper, new Object[] { roleId });
    }
    private static final class PermissionUsageDataMapper implements RowMapper<PermissionData> {
        private final DatabaseSpecificSQLGenerator sqlGenerator;
        PermissionUsageDataMapper(DatabaseSpecificSQLGenerator sqlGenerator) {
            this.sqlGenerator = sqlGenerator;
        }
        @Override
        public PermissionData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final String grouping = rs.getString("grouping");
            final String code = rs.getString("code");
            final String entityName = rs.getString("entityName");
            final String actionName = rs.getString("actionName");
            final Boolean selected = rs.getBoolean("selected");
            return PermissionData.instance(grouping, code, entityName, actionName, selected);
        }
        public String permissionSchema() {
            return "select p.grouping, p.code, p.entity_name as entityName, p.action_name as actionName, true as selected"
                    + " from m_permission p " + " where code not like '%\\_CHECKER'"
                    + " order by p.grouping, coalesce(entity_name, ''), p.code";
        }
        public String makerCheckerablePermissionSchema() {
            return "select p.grouping, p.code, p.entity_name as entityName, p.action_name as actionName, p.can_maker_checker as selected"
                    + " from m_permission p " + " where " + sqlGenerator.escape("grouping")
                    + " != 'special' and code not like 'READ_%' and code not like '%\\_CHECKER'"
                    + " order by p.grouping, coalesce(entity_name, ''), p.code";
        }
        public String rolePermissionSchema() {
            return "select p.grouping, p.code, p.entity_name as entityName, p.action_name as actionName, rp.role_id IS NOT NULL as selected "
                    + " from m_permission p " + " left join m_role_permission rp on rp.permission_id = p.id and rp.role_id = ? "
                    + " order by p.grouping, COALESCE(entity_name, ''), p.code";
        }
    }
}
