
package org.apache.fineract.infrastructure.reportmailingjob.data;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public enum ReportMailingJobStretchyReportParamDateOption {
    INVALID(0, "ReportMailingJobStretchyReportParamDateOption.INVALID", "Invalid"), TODAY(1,
            "ReportMailingJobStretchyReportParamDateOption.TODAY", "Today"), YESTERDAY(2,
                    "ReportMailingJobStretchyReportParamDateOption.YESTERDAY",
                    "Yesterday"), TOMORROW(3, "ReportMailingJobStretchyReportParamDateOption.TOMORROW", "Tomorrow");
    private final String code;
    private final String value;
    private final Integer id;
    ReportMailingJobStretchyReportParamDateOption(final Integer id, final String code, final String value) {
        this.value = value;
        this.code = code;
        this.id = id;
    }
    public static ReportMailingJobStretchyReportParamDateOption newInstance(final String value) {
        ReportMailingJobStretchyReportParamDateOption reportMailingJobStretchyReportParamDateOption = INVALID;
        if (StringUtils.equalsIgnoreCase(value, TODAY.value)) {
            reportMailingJobStretchyReportParamDateOption = TODAY;
        } else if (StringUtils.equalsIgnoreCase(value, YESTERDAY.value)) {
            reportMailingJobStretchyReportParamDateOption = YESTERDAY;
        } else if (StringUtils.equalsIgnoreCase(value, TOMORROW.value)) {
            reportMailingJobStretchyReportParamDateOption = TOMORROW;
        }
        return reportMailingJobStretchyReportParamDateOption;
    }
    public static ReportMailingJobStretchyReportParamDateOption newInstance(final Integer id) {
        ReportMailingJobStretchyReportParamDateOption reportMailingJobStretchyReportParamDateOption = INVALID;
        if (id.equals(TODAY.id)) {
            reportMailingJobStretchyReportParamDateOption = TODAY;
        } else if (id.equals(YESTERDAY.id)) {
            reportMailingJobStretchyReportParamDateOption = YESTERDAY;
        } else if (id.equals(TOMORROW.id)) {
            reportMailingJobStretchyReportParamDateOption = TOMORROW;
        }
        return reportMailingJobStretchyReportParamDateOption;
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
    public boolean isInvalid() {
        return this.equals(INVALID);
    }
    public boolean isValid() {
        return !this.isInvalid();
    }
    public static Object[] validValues() {
        List<Object> validValues = new ArrayList<>();
        for (ReportMailingJobStretchyReportParamDateOption constant : ReportMailingJobStretchyReportParamDateOption.values()) {
            if (constant.isValid()) {
                validValues.add(constant.value);
            }
        }
        return validValues.toArray();
    }
    public EnumOptionData toEnumOptionData() {
        final Long id = (this.id != null) ? this.id.longValue() : null;
        return new EnumOptionData(id, code, value);
    }
    public static List<EnumOptionData> validOptions() {
        List<EnumOptionData> options = new ArrayList<>();
        for (ReportMailingJobStretchyReportParamDateOption constant : ReportMailingJobStretchyReportParamDateOption.values()) {
            if (constant.isValid()) {
                options.add(constant.toEnumOptionData());
            }
        }
        return options;
    }
}
