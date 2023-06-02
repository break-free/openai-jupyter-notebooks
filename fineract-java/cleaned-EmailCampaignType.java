
package org.apache.fineract.infrastructure.campaigns.email.domain;
public enum EmailCampaignType {
    DIRECT(1, "emailCampaignStatusType.direct"), SCHEDULE(2, "emailCampaignStatusType.schedule"), TRIGGERED(3,
            "emailCampaignStatusType.triggered");
    private final Integer value;
    private final String code;
    EmailCampaignType(Integer value, String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return value;
    }
    public String getCode() {
        return code;
    }
    public static EmailCampaignType fromInt(final Integer typeValue) {
        EmailCampaignType type = null;
        switch (typeValue) {
            case 1:
                type = DIRECT;
            break;
            case 2:
                type = SCHEDULE;
            break;
            case 3:
                type = TRIGGERED;
            break;
        }
        return type;
    }
    public boolean isDirect() {
        return this.value.equals(EmailCampaignType.DIRECT.getValue());
    }
    public boolean isSchedule() {
        return this.value.equals(EmailCampaignType.SCHEDULE.getValue());
    }
    public boolean isTriggered() {
        return this.value.equals(EmailCampaignType.TRIGGERED.getValue());
    }
}
