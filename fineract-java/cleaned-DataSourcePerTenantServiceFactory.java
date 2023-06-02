
package org.apache.fineract.infrastructure.core.service;
import static org.apache.fineract.infrastructure.core.domain.FineractPlatformTenantConnection.toJdbcUrl;
import static org.apache.fineract.infrastructure.core.domain.FineractPlatformTenantConnection.toProtocol;
import com.zaxxer.hikari.HikariConfig;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenantConnection;
import org.apache.fineract.infrastructure.security.constants.TenantConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class DataSourcePerTenantServiceFactory {
    private final HikariConfig hikariConfig;
    private final FineractProperties fineractProperties;
    private final ApplicationContext context;
    private final DataSource tenantDataSource;
    private final HikariDataSourceFactory hikariDataSourceFactory;
    public DataSourcePerTenantServiceFactory(@Qualifier("hikariTenantDataSource") DataSource tenantDataSource, HikariConfig hikariConfig,
            FineractProperties fineractProperties, ApplicationContext context, HikariDataSourceFactory hikariDataSourceFactory) {
        this.hikariConfig = hikariConfig;
        this.fineractProperties = fineractProperties;
        this.context = context;
        this.tenantDataSource = tenantDataSource;
        this.hikariDataSourceFactory = hikariDataSourceFactory;
    }
    public DataSource createNewDataSourceFor(final FineractPlatformTenantConnection tenantConnection) {
        String protocol = toProtocol(tenantDataSource);
        String schemaServer = tenantConnection.getSchemaServer();
        String schemaPort = tenantConnection.getSchemaServerPort();
        String schemaName = tenantConnection.getSchemaName();
        String schemaUsername = tenantConnection.getSchemaUsername();
        String schemaPassword = tenantConnection.getSchemaPassword();
        String schemaConnectionParameters = tenantConnection.getSchemaConnectionParameters();
        if (fineractProperties.getMode().isReadOnlyMode()) {
            schemaServer = getPropertyValue(tenantConnection.getReadOnlySchemaServer(), TenantConstants.PROPERTY_RO_SCHEMA_SERVER_NAME,
                    schemaServer);
            schemaPort = getPropertyValue(tenantConnection.getReadOnlySchemaServerPort(), TenantConstants.PROPERTY_RO_SCHEMA_SERVER_PORT,
                    schemaPort);
            schemaName = getPropertyValue(tenantConnection.getReadOnlySchemaName(), TenantConstants.PROPERTY_RO_SCHEMA_SCHEMA_NAME,
                    schemaName);
            schemaUsername = getPropertyValue(tenantConnection.getReadOnlySchemaUsername(), TenantConstants.PROPERTY_RO_SCHEMA_USERNAME,
                    schemaUsername);
            schemaPassword = getPropertyValue(tenantConnection.getReadOnlySchemaPassword(), TenantConstants.PROPERTY_RO_SCHEMA_PASSWORD,
                    schemaPassword);
            schemaConnectionParameters = getPropertyValue(tenantConnection.getReadOnlySchemaConnectionParameters(),
                    TenantConstants.PROPERTY_RO_SCHEMA_CONNECTION_PARAMETERS, schemaConnectionParameters);
        }
        String jdbcUrl = toJdbcUrl(protocol, schemaServer, schemaPort, schemaName, schemaConnectionParameters);
        log.debug("{}", jdbcUrl);
        HikariConfig config = new HikariConfig();
        config.setReadOnly(fineractProperties.getMode().isReadOnlyMode());
        config.setJdbcUrl(jdbcUrl);
        config.setPoolName(schemaName + "_pool");
        config.setUsername(schemaUsername);
        config.setPassword(schemaPassword);
        config.setMinimumIdle(tenantConnection.getInitialSize());
        config.setMaximumPoolSize(tenantConnection.getMaxActive());
        config.setValidationTimeout(tenantConnection.getValidationInterval());
        config.setDriverClassName(hikariConfig.getDriverClassName());
        config.setConnectionTestQuery(hikariConfig.getConnectionTestQuery());
        config.setAutoCommit(hikariConfig.isAutoCommit());
        config.setRegisterMbeans(true);
        config.setDataSourceProperties(hikariConfig.getDataSourceProperties());
        return hikariDataSourceFactory.create(config);
    }
    private String getPropertyValue(final String baseValue, final String propertyName, final String defaultValue) {
        if (null != baseValue) {
            return baseValue;
        }
        if (context == null) {
            return defaultValue;
        }
        return context.getEnvironment().getProperty(propertyName, defaultValue);
    }
}
