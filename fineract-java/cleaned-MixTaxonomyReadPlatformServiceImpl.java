
package org.apache.fineract.mix.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.fineract.mix.data.MixTaxonomyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class MixTaxonomyReadPlatformServiceImpl implements MixTaxonomyReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    private final MixTaxonomyMapper mixTaxonomyMapper;
    @Autowired
    public MixTaxonomyReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mixTaxonomyMapper = new MixTaxonomyMapper();
    }
    private static final class MixTaxonomyMapper implements RowMapper<MixTaxonomyData> {
        public String schema() {
            return "tx.id as id, name, dimension, type, description, prefix "
                    + "from mix_taxonomy tx left join mix_xbrl_namespace xn on tx.namespace_id=xn.id";
        }
        @Override
        public MixTaxonomyData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final long id = rs.getLong("id");
            final String name = rs.getString("name");
            final String namespace = rs.getString("prefix");
            final String dimension = rs.getString("dimension");
            final Integer type = rs.getInt("type");
            final String desc = rs.getString("description");
            return new MixTaxonomyData(id, name, namespace, dimension, type, desc);
        }
    }
    @Override
    public List<MixTaxonomyData> retrieveAll() {
        final String sql = "select " + this.mixTaxonomyMapper.schema() + " order by id";
        return this.jdbcTemplate.query(sql, this.mixTaxonomyMapper); 
    }
    @Override
    public MixTaxonomyData retrieveOne(final Long id) {
        final String sql = "select " + this.mixTaxonomyMapper.schema() + " where tx.id = ? ";
        return this.jdbcTemplate.queryForObject(sql, this.mixTaxonomyMapper, new Object[] { id }); 
    }
}
