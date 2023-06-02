
package org.apache.fineract.infrastructure.core.service;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.fineract.infrastructure.core.service.database.DatabaseSpecificSQLGenerator;
import org.apache.fineract.infrastructure.core.service.database.DatabaseTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class PaginationHelper {
    private final DatabaseSpecificSQLGenerator sqlGenerator;
    private final DatabaseTypeResolver databaseTypeResolver;
    @Autowired
    public PaginationHelper(DatabaseSpecificSQLGenerator sqlGenerator, DatabaseTypeResolver databaseTypeResolver) {
        this.sqlGenerator = sqlGenerator;
        this.databaseTypeResolver = databaseTypeResolver;
    }
    public <E> Page<E> fetchPage(final JdbcTemplate jt, final String sqlFetchRows, final Object[] args, final RowMapper<E> rowMapper) {
        final List<E> items = jt.query(sqlFetchRows, rowMapper, args); 
        final String sqlCountRows = sqlGenerator.countLastExecutedQueryResult(sqlFetchRows);
        final int totalFilteredRecords;
        if (databaseTypeResolver.isMySQL()) {
            totalFilteredRecords = jt.queryForObject(sqlCountRows, Integer.class); 
        } else {
            totalFilteredRecords = jt.queryForObject(sqlCountRows, Integer.class, args); 
        }
        return new Page<>(items, totalFilteredRecords);
    }
    public <E> Page<Long> fetchPage(JdbcTemplate jdbcTemplate, String sql, Class<Long> type) {
        final List<Long> items = jdbcTemplate.queryForList(sql, type);
        String sqlCountRows = sqlGenerator.countLastExecutedQueryResult(sql);
        Integer totalFilteredRecords = jdbcTemplate.queryForObject(sqlCountRows, Integer.class);
        return new Page<>(items, ObjectUtils.defaultIfNull(totalFilteredRecords, 0));
    }
}
