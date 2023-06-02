
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class EmailCampaignMustBeClosedToEditException extends AbstractPlatformDomainRuleException {
    public EmailCampaignMustBeClosedToEditException(final Long resourceId) {
        super("error.msg.email.campaign.cannot.be.updated",
                "Campaign with identifier " + resourceId + " cannot be updated as it is not in `Closed` state.", resourceId);
    }
}
