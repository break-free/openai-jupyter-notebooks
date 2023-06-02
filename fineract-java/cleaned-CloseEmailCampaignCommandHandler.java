
package org.apache.fineract.infrastructure.campaigns.email.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.campaigns.email.service.EmailCampaignWritePlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "EMAIL_CAMPAIGN", action = "CLOSE")
public class CloseEmailCampaignCommandHandler implements NewCommandSourceHandler {
    private final EmailCampaignWritePlatformService emailCampaignWritePlatformService;
    @Autowired
    public CloseEmailCampaignCommandHandler(final EmailCampaignWritePlatformService emailCampaignWritePlatformService) {
        this.emailCampaignWritePlatformService = emailCampaignWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.emailCampaignWritePlatformService.closeEmailCampaign(command.entityId(), command);
    }
}
