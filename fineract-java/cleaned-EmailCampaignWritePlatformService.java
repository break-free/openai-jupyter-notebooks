
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.util.HashMap;
import org.apache.fineract.infrastructure.campaigns.email.data.PreviewCampaignMessage;
import org.apache.fineract.infrastructure.campaigns.email.domain.EmailCampaign;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
public interface EmailCampaignWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long resourceId, JsonCommand command);
    CommandProcessingResult delete(Long resourceId);
    CommandProcessingResult activateEmailCampaign(Long campaignId, JsonCommand command);
    CommandProcessingResult closeEmailCampaign(Long campaignId, JsonCommand command);
    CommandProcessingResult reactivateEmailCampaign(Long campaignId, JsonCommand command);
    void storeTemplateMessageIntoEmailOutBoundTable() throws JobExecutionException;
    PreviewCampaignMessage previewMessage(JsonQuery query);
    void sendEmailMessage() throws JobExecutionException;
    void insertDirectCampaignIntoEmailOutboundTable(Loan loan, EmailCampaign emailCampaign, HashMap<String, String> campaignParams);
}
