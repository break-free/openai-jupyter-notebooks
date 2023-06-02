
package org.apache.fineract.infrastructure.reportmailingjob.data;
import org.apache.commons.lang3.StringUtils;
public enum ReportMailingJobPreviousRunStatus {
    INVALID(-1, "ReportMailingJobPreviousRunStatus.INVALID", "Invalid"), SUCCESS(1, "ReportMailingJobPreviousRunStatus.SUCCESS",
            "Success"), ERROR(0, "ReportMailingJobPreviousRunStatus.ERROR", "Error");
    private final String code;
    private final String value;
    private final Integer id;
    ReportMailingJobPreviousRunStatus(final Integer id, final String code, final String value) {
        this.value = value;
        this.code = code;
        this.id = id;
    }
    public static ReportMailingJobPreviousRunStatus newInstance(final String value) {
        ReportMailingJobPreviousRunStatus previousRunStatus = INVALID;
        if (StringUtils.equalsIgnoreCase(value, SUCCESS.value)) {
            previousRunStatus = SUCCESS;
        } else if (StringUtils.equalsIgnoreCase(value, ERROR.value)) {
            previousRunStatus = ERROR;
        }
        return previousRunStatus;
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
    public boolean isSuccess() {
        return this.value.equals(SUCCESS.getValue());
    }
    public boolean isError() {
        return this.value.equals(ERROR.getValue());
    }
}
