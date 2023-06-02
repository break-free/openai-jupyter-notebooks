
package org.apache.fineract.infrastructure.dataqueries.service;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.StreamingOutput;
import org.apache.fineract.infrastructure.dataqueries.data.GenericResultsetData;
import org.apache.fineract.infrastructure.dataqueries.data.ReportData;
import org.apache.fineract.infrastructure.dataqueries.data.ReportParameterData;
import org.apache.fineract.useradministration.domain.AppUser;
public interface ReadReportingService {
    Collection<ReportData> retrieveReportList();
    ReportData retrieveReport(Long id);
    String getReportType(String reportName, boolean isSelfServiceUserReport, boolean isParameterType);
    Collection<ReportParameterData> getAllowedParameters();
    String retrieveReportPDF(String name, String type, Map<String, String> extractedQueryParams, boolean isSelfServiceUserReport);
    StreamingOutput retrieveReportCSV(String name, String type, Map<String, String> extractedQueryParams, boolean isSelfServiceUserReport);
    GenericResultsetData retrieveGenericResultset(String name, String type, Map<String, String> extractedQueryParams,
            boolean isSelfServiceUserReport);
    GenericResultsetData retrieveGenericResultSetForSmsEmailCampaign(String name, String type, Map<String, String> extractedQueryParams);
    ByteArrayOutputStream generatePentahoReportAsOutputStream(String reportName, String outputTypeParam, Map<String, String> queryParams,
            Locale locale, AppUser runReportAsUser, StringBuilder errorLog);
}
