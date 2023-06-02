
package org.apache.fineract.infrastructure.campaigns.email.domain;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class EmailCampaignStatusEnumerations {
    private EmailCampaignStatusEnumerations() {
    }
    public static EnumOptionData status(final Integer statusId) {
        return status(EmailCampaignStatus.fromInt(statusId));
    }
    public static EnumOptionData status(final EmailCampaignStatus status) {
        EnumOptionData optionData = new EnumOptionData(EmailCampaignStatus.INVALID.getValue().longValue(),
                EmailCampaignStatus.INVALID.getCode(), "Invalid");
        switch (status) {
            case INVALID:
                optionData = new EnumOptionData(EmailCampaignStatus.INVALID.getValue().longValue(), EmailCampaignStatus.INVALID.getCode(),
                        "Invalid");
            break;
            case PENDING:
                optionData = new EnumOptionData(EmailCampaignStatus.PENDING.getValue().longValue(), EmailCampaignStatus.PENDING.getCode(),
                        "Pending");
            break;
            case ACTIVE:
                optionData = new EnumOptionData(EmailCampaignStatus.ACTIVE.getValue().longValue(), EmailCampaignStatus.ACTIVE.getCode(),
                        "active");
            break;
            case CLOSED:
                optionData = new EnumOptionData(EmailCampaignStatus.CLOSED.getValue().longValue(), EmailCampaignStatus.CLOSED.getCode(),
                        "closed");
            break;
        }
        return optionData;
    }
}
