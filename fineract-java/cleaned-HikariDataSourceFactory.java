
package org.apache.fineract.infrastructure.core.service;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;
@Component
public class HikariDataSourceFactory {
    public HikariDataSource create(HikariConfig config) {
        return new HikariDataSource(config);
    }
}
