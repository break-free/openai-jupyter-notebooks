
package org.apache.fineract.infrastructure.creditbureau.service;
import java.io.File;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
public interface CreditReportWritePlatformService {
    CommandProcessingResult getCreditReport(JsonCommand command);
    CommandProcessingResult saveCreditReport(Long organisationCreditBureauId, String nationalId, JsonCommand command);
    CommandProcessingResult deleteCreditReport(Long creditBureauId, JsonCommand command);
    String addCreditReport(Long creditBureauId, File creditReport, FormDataContentDisposition fileDetail);
}
