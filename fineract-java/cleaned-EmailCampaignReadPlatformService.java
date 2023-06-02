
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailBusinessRulesData;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailCampaignData;
public interface EmailCampaignReadPlatformService {
    Collection<EmailBusinessRulesData> retrieveAll();
    EmailBusinessRulesData retrieveOneTemplate(Long resourceId);
    EmailCampaignData retrieveOne(Long resourceId);
    Collection<EmailCampaignData> retrieveAllCampaign();
    Collection<EmailCampaignData> retrieveAllScheduleActiveCampaign();
}
