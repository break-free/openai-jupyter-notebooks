
package org.apache.fineract.infrastructure.campaigns.email.domain;
public enum ScheduledEmailStretchyReportParamDateOption {
    INVALID(0, "scheduledEmailStretchyReportParamDateOption.invalid", "invalid"), TODAY(1,
            "scheduledEmailStretchyReportParamDateOption.today", "today"),
    TOMORROW(3, "scheduledEmailStretchyReportParamDateOption.tomorrow", "tomorrow");
    private final String code;
    private final String value;
    private final Integer id;
    ScheduledEmailStretchyReportParamDateOption(final Integer id, final String code, final String value) {
        this.value = value;
        this.code = code;
        this.id = id;
    }
    public static ScheduledEmailStretchyReportParamDateOption instance(final String value) {
        ScheduledEmailStretchyReportParamDateOption scheduledEmailStretchyReportParamDateOption = INVALID;
        switch (value) {
            case "today":
                scheduledEmailStretchyReportParamDateOption = TODAY;
            break;
            case "tomorrow":
                scheduledEmailStretchyReportParamDateOption = TOMORROW;
            break;
        }
        return scheduledEmailStretchyReportParamDateOption;
    }
    public static ScheduledEmailStretchyReportParamDateOption instance(final Integer id) {
        ScheduledEmailStretchyReportParamDateOption scheduledEmailStretchyReportParamDateOption = INVALID;
        switch (id) {
            case 1:
                scheduledEmailStretchyReportParamDateOption = TODAY;
            break;
            case 3:
                scheduledEmailStretchyReportParamDateOption = TOMORROW;
            break;
        }
        return scheduledEmailStretchyReportParamDateOption;
    }
    public String getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }
    public Integer getId() {
        return id;
    }
    public static Object[] validValues() {
        return new Object[] { TODAY.value, TOMORROW.value };
    }
}
