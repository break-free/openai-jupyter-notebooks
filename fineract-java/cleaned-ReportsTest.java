
package org.apache.fineract.integrationtests.client;
import java.io.IOException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
public class ReportsTest extends IntegrationTest {
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
    }
    @Test
    void listReports() {
        assertThat(ok(fineract().reports.retrieveReportList())).hasSize(124);
    }
    @Test
    void runClientListingTableReport() {
        assertThat(ok(fineract().reportsRun.runReportGetData("Client Listing", Map.of("R_officeId", "1"), false)).getColumnHeaders().get(0)
                .getColumnName()).isEqualTo("Office/Branch");
    }
    @Test 
    void runReportCategory() throws IOException {
        var req = new Request.Builder().url(fineract().baseURL().resolve(
                "/fineract-provider/api/v1/runreports/reportCategoryList?R_reportCategory=Fund&genericResultSet=false&parameterType=true&tenantIdentifier=default"))
                .build();
        try (var response = fineract().okHttpClient().newCall(req).execute()) {
            assertThat(response.code()).isEqualTo(200);
        }
    }
    @Test
    void runExpectedPaymentsPentahoReportWithoutPlugin() {
        assertThat(fineract().reportsRun.runReportGetFile("Expected Payments By Date - Formatted", Map.of("R_endDate", "2013-04-30",
                "R_loanOfficerId", "-1", "R_officeId", "1", "R_startDate", "2013-04-16", "output-type", "PDF"), false)).hasHttpStatus(503);
    }
    @Test
    @Disabled
    void runExpectedPaymentsPentahoReport() {
        ResponseBody r = ok(fineract().reportsRun.runReportGetFile("Expected Payments By Date - Formatted", Map.of("R_endDate",
                "2013-04-30", "R_loanOfficerId", "-1", "R_officeId", "1", "R_startDate", "2013-04-16", "output-type", "PDF"), false));
        assertThat(r.contentType()).isEqualTo(MediaType.get("application/pdf"));
    }
    @Test
    void testTrialBalanceTableReportRunsSuccessfully() {
        assertThat(fineract().reportsRun.runReportGetData("Trial Balance Table",
                Map.of("R_endDate", "2013-04-30", "R_officeId", "1", "R_startDate", "2013-04-16"), false)).hasHttpStatus(200);
    }
    @Test
    void testIncomeStatementTableReportRunsSuccessfully() {
        assertThat(fineract().reportsRun.runReportGetData("Income Statement Table",
                Map.of("R_endDate", "2013-04-30", "R_officeId", "1", "R_startDate", "2013-04-16"), false)).hasHttpStatus(200);
    }
    @Test
    void testGeneralLedgerReportTableReportRunsSuccessfully() {
        assertThat(fineract().reportsRun.runReportGetData("GeneralLedgerReport Table",
                Map.of("R_endDate", "2013-04-30", "R_officeId", "1", "R_startDate", "2013-04-16", "R_GLAccountNO", "1"), false))
                        .hasHttpStatus(200);
    }
    @Test
    void testBalanceSheetTableReportRunsSuccessfully() {
        assertThat(
                fineract().reportsRun.runReportGetData("Balance Sheet Table", Map.of("R_endDate", "2013-04-30", "R_officeId", "1"), false))
                        .hasHttpStatus(200);
    }
}
