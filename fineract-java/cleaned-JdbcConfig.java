
package org.apache.fineract.infrastructure.core.config;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
@Configuration
public class JdbcConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(RoutingDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(RoutingDataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
