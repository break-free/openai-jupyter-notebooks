
package org.apache.fineract.infrastructure.creditbureau.service;
import java.io.File;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauReportData;
import org.apache.fineract.infrastructure.creditbureau.domain.CreditBureauToken;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
public interface ThitsaWorksCreditBureauIntegrationWritePlatformService {
    CreditBureauToken createToken(Long creditBureauID);
    Long extractUniqueId(String jsonResult);
    String okHttpConnectionMethod(String userName, String password, String subscriptionKey, String subscriptionId, String url, String token,
            File report, FormDataContentDisposition fileDetail, Long uniqueId, String nrcId, String process);
    CreditBureauReportData getCreditReportFromThitsaWorks(JsonCommand command);
    String addCreditReport(Long bureauId, File creditReport, FormDataContentDisposition fileDetail);
}
