
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class EmailCampaignNotFound extends AbstractPlatformResourceNotFoundException {
    public EmailCampaignNotFound(final Long resourceId) {
        super("error.msg.email.campaign.identifier.not.found", "EMAIL_CAMPAIGN with identifier `" + resourceId + "` does not exist",
                resourceId);
    }
    public EmailCampaignNotFound(final Long resourceId, EmptyResultDataAccessException e) {
        super("error.msg.email.campaign.identifier.not.found", "EMAIL_CAMPAIGN with identifier `" + resourceId + "` does not exist",
                resourceId, e);
    }
}
