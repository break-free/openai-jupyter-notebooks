
package org.apache.fineract.infrastructure.campaigns.sms.service;
import java.util.Map;
import org.apache.fineract.infrastructure.campaigns.sms.data.CampaignPreviewData;
import org.apache.fineract.infrastructure.campaigns.sms.domain.SmsCampaign;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public interface SmsCampaignWritePlatformService {
    CommandProcessingResult create(JsonCommand command);
    CommandProcessingResult update(Long resourceId, JsonCommand command);
    CommandProcessingResult delete(Long resourceId);
    CommandProcessingResult activateSmsCampaign(Long campaignId, JsonCommand command);
    CommandProcessingResult closeSmsCampaign(Long campaignId, JsonCommand command);
    CommandProcessingResult reactivateSmsCampaign(Long campaignId, JsonCommand command);
    void insertDirectCampaignIntoSmsOutboundTable(Loan loan, SmsCampaign smsCampaign);
    String compileSmsTemplate(String textMessageTemplate, String campaignName, Map<String, Object> smsParams);
    CampaignPreviewData previewMessage(JsonQuery query);
    void storeTemplateMessageIntoSmsOutBoundTable() throws JobExecutionException;
    void insertDirectCampaignIntoSmsOutboundTable(Client client, SmsCampaign smsCampaign);
    void insertDirectCampaignIntoSmsOutboundTable(SavingsAccount savingsAccount, SmsCampaign smsCampaign);
}
