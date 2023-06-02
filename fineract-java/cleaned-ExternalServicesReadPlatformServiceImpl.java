
package org.apache.fineract.infrastructure.configuration.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.fineract.infrastructure.configuration.data.ExternalServicesData;
import org.apache.fineract.infrastructure.configuration.exception.ExternalServiceConfigurationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
@Service
public class ExternalServicesReadPlatformServiceImpl implements ExternalServicesReadPlatformService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ExternalServicesReadPlatformServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ExternalServicesData getExternalServiceDetailsByServiceName(String serviceName) {
        final ResultSetExtractor<ExternalServicesData> resultSetExtractor = new ExternalServicesDetailsDataExtractor();
        String serviceNameToUse = null;
        switch (serviceName) {
            case "S3":
                serviceNameToUse = ExternalServicesConstants.S3_SERVICE_NAME;
            break;
            case "SMTP":
                serviceNameToUse = ExternalServicesConstants.SMTP_SERVICE_NAME;
            break;
            case "SMS":
                serviceNameToUse = ExternalServicesConstants.SMS_SERVICE_NAME;
            break;
            case "NOTIFICATION":
                serviceNameToUse = ExternalServicesConstants.NOTIFICATION_SERVICE_NAME;
            break;
            default:
                throw new ExternalServiceConfigurationNotFoundException(serviceName);
        }
        final String sql = "SELECT es.name as name, es.id as id FROM c_external_service es where es.name='" + serviceNameToUse + "'";
        final ExternalServicesData externalServicesData = this.jdbcTemplate.query(sql, resultSetExtractor); 
        return externalServicesData;
    }
    private static final class ExternalServicesDetailsDataExtractor implements ResultSetExtractor<ExternalServicesData> {
        @Override
        public ExternalServicesData extractData(ResultSet rs) throws SQLException, DataAccessException {
            Long id = (long) 0;
            String name = null;
            while (rs.next()) {
                name = rs.getString("name");
                id = rs.getLong("id");
            }
            return new ExternalServicesData(id, name);
        }
    }
}
