
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailData;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
public interface EmailReadPlatformService {
    Collection<EmailData> retrieveAll();
    EmailData retrieveOne(Long resourceId);
    Collection<EmailData> retrieveAllPending(SearchParameters searchParameters);
    Collection<EmailData> retrieveAllSent(SearchParameters searchParameters);
    Collection<EmailData> retrieveAllDelivered(Integer limit);
    Collection<EmailData> retrieveAllFailed(SearchParameters searchParameters);
    Page<EmailData> retrieveEmailByStatus(Integer limit, Integer status, LocalDate dateFrom, LocalDate dateTo);
    List<Long> retrieveExternalIdsOfAllSent(Integer limit);
}
