
package org.apache.fineract.infrastructure.core.service;
import javax.sql.DataSource;
public interface RoutingDataSourceService {
    DataSource retrieveDataSource();
}
