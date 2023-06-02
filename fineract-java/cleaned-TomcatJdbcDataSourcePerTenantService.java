
package org.apache.fineract.infrastructure.core.service;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenantConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class TomcatJdbcDataSourcePerTenantService implements RoutingDataSourceService {
    private final Map<Long, DataSource> tenantToDataSourceMap = new HashMap<>(1);
    private final DataSource tenantDataSource;
    private final DataSourcePerTenantServiceFactory dataSourcePerTenantServiceFactory;
    @Autowired
    public TomcatJdbcDataSourcePerTenantService(final @Qualifier("hikariTenantDataSource") DataSource tenantDataSource,
            final DataSourcePerTenantServiceFactory dataSourcePerTenantServiceFactory) {
        this.tenantDataSource = tenantDataSource;
        this.dataSourcePerTenantServiceFactory = dataSourcePerTenantServiceFactory;
    }
    @Override
    public DataSource retrieveDataSource() {
        DataSource tenantDataSource = this.tenantDataSource;
        final FineractPlatformTenant tenant = ThreadLocalContextUtil.getTenant();
        if (tenant != null) {
            final FineractPlatformTenantConnection tenantConnection = tenant.getConnection();
            synchronized (this.tenantToDataSourceMap) {
                DataSource possibleDS = this.tenantToDataSourceMap.get(tenantConnection.getConnectionId());
                if (possibleDS != null) {
                    tenantDataSource = possibleDS;
                } else {
                    tenantDataSource = dataSourcePerTenantServiceFactory.createNewDataSourceFor(tenantConnection);
                    this.tenantToDataSourceMap.put(tenantConnection.getConnectionId(), tenantDataSource);
                }
            }
        }
        return tenantDataSource;
    }
}
