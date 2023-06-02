
package org.apache.fineract.infrastructure.campaigns.email.data;
import org.apache.fineract.infrastructure.campaigns.email.domain.ScheduledEmailAttachmentFileFormat;
import org.apache.fineract.infrastructure.campaigns.email.domain.ScheduledEmailStretchyReportParamDateOption;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class ScheduledEmailEnumerations {
    private ScheduledEmailEnumerations() {
    }
    public static EnumOptionData emailAttachementFileFormat(final Integer emailAttachementFileFormatId) {
        return emailAttachementFileFormat(ScheduledEmailAttachmentFileFormat.instance(emailAttachementFileFormatId));
    }
    public static EnumOptionData emailAttachementFileFormat(final String emailAttachementFileFormatString) {
        return emailAttachementFileFormat(ScheduledEmailAttachmentFileFormat.instance(emailAttachementFileFormatString));
    }
    public static EnumOptionData emailAttachementFileFormat(final ScheduledEmailAttachmentFileFormat emailAttachementFileFormat) {
        EnumOptionData enumOptionData = null;
        if (emailAttachementFileFormat != null) {
            enumOptionData = new EnumOptionData(emailAttachementFileFormat.getId().longValue(), emailAttachementFileFormat.getCode(),
                    emailAttachementFileFormat.getValue());
        }
        return enumOptionData;
    }
    public static EnumOptionData stretchyReportDateOption(
            final ScheduledEmailStretchyReportParamDateOption reportMailingJobStretchyReportParamDateOption) {
        EnumOptionData enumOptionData = null;
        if (reportMailingJobStretchyReportParamDateOption != null) {
            enumOptionData = new EnumOptionData(reportMailingJobStretchyReportParamDateOption.getId().longValue(),
                    reportMailingJobStretchyReportParamDateOption.getCode(), reportMailingJobStretchyReportParamDateOption.getValue());
        }
        return enumOptionData;
    }
}
