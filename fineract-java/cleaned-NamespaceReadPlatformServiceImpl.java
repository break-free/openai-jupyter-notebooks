
package org.apache.fineract.mix.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.fineract.mix.data.NamespaceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class NamespaceReadPlatformServiceImpl implements NamespaceReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final NamespaceMapper namespaceMapper;
    @Autowired
    public NamespaceReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namespaceMapper = new NamespaceMapper();
    }
    private static final class NamespaceMapper implements RowMapper<NamespaceData> {
        public String schema() {
            return "select id, prefix, url " + "from mix_xbrl_namespace";
        }
        @Override
        public NamespaceData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final long id = rs.getLong("id");
            final String prefix = rs.getString("prefix");
            final String url = rs.getString("url");
            return new NamespaceData(id, prefix, url);
        }
    }
    @Override
    public NamespaceData retrieveNamespaceById(final Long id) {
        final String sql = this.namespaceMapper.schema() + " where id= ? ";
        return this.jdbcTemplate.queryForObject(sql, this.namespaceMapper, new Object[] { id }); 
    }
    @Override
    public NamespaceData retrieveNamespaceByPrefix(final String prefix) {
        final String sql = this.namespaceMapper.schema() + " where prefix = ? ";
        return this.jdbcTemplate.queryForObject(sql, this.namespaceMapper, new Object[] { prefix }); 
    }
}
