
package org.apache.fineract.integrationtests.common.report;
import java.util.Map;
public class ReportData {
    private Long reportId;
    private String reportName;
    private String reportType;
    private String reportSubType;
    private String reportDescription;
    private Map<String, Object> reportParamName;
    public ReportData() {}
    public ReportData(final Long reportId, final String reportName, final String reportType, final String reportSubType,
            final Map<String, Object> reportParamName, final String reportDescription) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportType = reportType;
        this.reportSubType = reportSubType;
        this.reportParamName = reportParamName;
        this.reportDescription = reportDescription;
    }
    public static ReportData instance(final Long reportId, final String reportName, final String reportType, final String reportSubType,
            final Map<String, Object> reportParamName, final String reportDescription) {
        return new ReportData(reportId, reportName, reportType, reportSubType, reportParamName, reportDescription);
    }
    public Map<String, Object> getReportParamName() {
        return reportParamName;
    }
    public String getReportType() {
        return reportType;
    }
    public String getReportSubType() {
        return this.reportSubType;
    }
    public String getReportName() {
        return reportName;
    }
    public Long getReportId() {
        return reportId;
    }
    public String getReportDescription() {
        return reportDescription;
    }
}
