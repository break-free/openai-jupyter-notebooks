
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.CreditReportData;
public interface CreditReportReadPlatformService {
    Collection<CreditReportData> retrieveCreditReportDetails(Long creditBureauId);
}
