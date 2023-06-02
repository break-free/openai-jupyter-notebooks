
package org.apache.fineract.infrastructure.sms.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.sms.data.SmsData;
public interface SmsReadPlatformService {
    Collection<SmsData> retrieveAll();
    SmsData retrieveOne(Long resourceId);
    Collection<SmsData> retrieveAllPending(Long campaignId, Integer limit);
    Collection<SmsData> retrieveAllSent(Integer limit);
    Collection<SmsData> retrieveAllDelivered(Integer limit);
    Collection<SmsData> retrieveAllFailed(Integer limit);
    Page<SmsData> retrieveSmsByStatus(Long campaignId, SearchParameters searchParameters, Integer status, LocalDate dateFrom,
            LocalDate dateTo);
    List<Long> retrieveExternalIdsOfAllSent(Integer limit);
    Page<Long> retrieveAllWaitingForDeliveryReport(Integer limit);
    List<Long> retrieveAllPending(Integer limit);
}
