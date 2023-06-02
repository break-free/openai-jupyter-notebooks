
package org.apache.fineract.infrastructure.campaigns.sms.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.campaigns.sms.data.SmsCampaignData;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
public interface SmsCampaignReadPlatformService {
    SmsCampaignData retrieveOne(Long campaignId);
    Page<SmsCampaignData> retrieveAll(SearchParameters searchParameters);
    SmsCampaignData retrieveTemplate(String reportType);
    Collection<SmsCampaignData> retrieveAllScheduleActiveCampaign();
}
