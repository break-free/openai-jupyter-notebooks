
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class EmailCampaignMustBeClosedToBeDeletedException extends AbstractPlatformDomainRuleException {
    public EmailCampaignMustBeClosedToBeDeletedException(final Long resourceId) {
        super("error.msg.email.campaign.cannot.be.deleted",
                "Campaign with identifier " + resourceId + " cannot be deleted as it is not in `Closed` state.", resourceId);
    }
}
