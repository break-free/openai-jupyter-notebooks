
package org.apache.fineract;
import static org.mockito.Mockito.mock;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.service.database.DatabaseIndependentQueryService;
import org.apache.fineract.infrastructure.core.service.migration.ExtendedSpringLiquibaseFactory;
import org.apache.fineract.infrastructure.core.service.migration.TenantDataSourceFactory;
import org.apache.fineract.infrastructure.core.service.migration.TenantDatabaseStateVerifier;
import org.apache.fineract.infrastructure.core.service.migration.TenantDatabaseUpgradeService;
import org.apache.fineract.infrastructure.jobs.service.JobRegisterService;
import org.apache.fineract.infrastructure.security.service.TenantDetailsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, GsonAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
        LiquibaseAutoConfiguration.class })
@EnableTransactionManagement
@EnableWebSecurity
@EnableConfigurationProperties({ FineractProperties.class, LiquibaseProperties.class })
@ComponentScan(basePackages = "org.apache.fineract")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestConfiguration {
    @Bean
    public TenantDataSourceFactory tenantDataSourceFactory() {
        return new TenantDataSourceFactory(null) {
            @Override
            public DataSource create(FineractPlatformTenant tenant) {
                return mock(DataSource.class);
            }
        };
    }
    @Bean
    public HikariDataSource tenantDataSource() {
        return mock(HikariDataSource.class, Mockito.RETURNS_MOCKS);
    }
    @Bean
    public TenantDetailsService tenantDetailsService() {
        return mock(TenantDetailsService.class, Mockito.RETURNS_MOCKS);
    }
    @Bean
    public ExtendedSpringLiquibaseFactory liquibaseFactory() {
        return mock(ExtendedSpringLiquibaseFactory.class, Mockito.RETURNS_MOCKS);
    }
    @Bean
    public DatabaseIndependentQueryService databaseIndependentQueryService() {
        return mock(DatabaseIndependentQueryService.class, Mockito.RETURNS_MOCKS);
    }
    @Bean
    public TenantDatabaseStateVerifier tenantDatabaseStateVerifier(DatabaseIndependentQueryService databaseIndependentQueryService,
            LiquibaseProperties liquibaseProperties, FineractProperties fineractProperties) {
        return new TenantDatabaseStateVerifier(liquibaseProperties, databaseIndependentQueryService, fineractProperties);
    }
    @Bean
    public TenantDatabaseUpgradeService tenantDatabaseUpgradeService(TenantDetailsService tenantDetailsService,
            HikariDataSource tenantDataSource, TenantDatabaseStateVerifier tenantDatabaseStateVerifier,
            ExtendedSpringLiquibaseFactory liquibaseFactory, TenantDataSourceFactory tenantDataSourceFactory,
            FineractProperties fineractProperties) {
        return new TenantDatabaseUpgradeService(tenantDetailsService, tenantDataSource, fineractProperties, tenantDatabaseStateVerifier,
                liquibaseFactory, tenantDataSourceFactory);
    }
    @Bean
    public JobRegisterService jobRegisterServiceImpl() {
        JobRegisterService mockJobRegisterService = mock(JobRegisterService.class);
        return mockJobRegisterService;
    }
    @Bean
    public DataSource hikariTenantDataSource() {
        DataSource mockDataSource = mock(DataSource.class, Mockito.RETURNS_MOCKS);
        return mockDataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(JdbcTemplate.class);
    }
}
