
package org.apache.fineract.infrastructure.report.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.fineract.infrastructure.security.utils.SQLInjectionValidator;
public interface ReportingProcessService {
    Response processRequest(String reportName, MultivaluedMap<String, String> queryParams);
    default Map<String, String> getReportParams(final MultivaluedMap<String, String> queryParams) {
        final Map<String, String> reportParams = new HashMap<>();
        final Set<String> keys = queryParams.keySet();
        for (final String k : keys) {
            if (k.startsWith("R_")) {
                String pKey = "${" + k.substring(2) + "}";
                String pValue = queryParams.get(k).get(0);
                SQLInjectionValidator.validateSQLInput(pValue);
                reportParams.put(pKey, pValue);
            }
        }
        return reportParams;
    }
}
