
package org.apache.fineract.infrastructure.campaigns.email.data;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.infrastructure.dataqueries.data.ReportData;
public final class EmailData {
    private final Long id;
    private final Long groupId;
    private final Long clientId;
    private final Long staffId;
    private final EnumOptionData status;
    private final String emailAddress;
    private final String emailSubject;
    private final String emailMessage;
    private final EnumOptionData emailAttachmentFileFormat;
    private final ReportData stretchyReport;
    private final String stretchyReportParamMap;
    private final List<EnumOptionData> emailAttachmentFileFormatOptions;
    private final List<EnumOptionData> stretchyReportParamDateOptions;
    private final String campaignName;
    private final LocalDate sentDate;
    private final String errorMessage;
    public static EmailData instance(final Long id, final Long groupId, final Long clientId, final Long staffId,
            final EnumOptionData status, final String emailAddress, final String emailSubject, final String message,
            final EnumOptionData emailAttachmentFileFormat, final ReportData stretchyReport, final String stretchyReportParamMap,
            final List<EnumOptionData> emailAttachmentFileFormatOptions, final List<EnumOptionData> stretchyReportParamDateOptions,
            final String campaignName, final LocalDate sentDate, final String errorMessage) {
        return new EmailData(id, groupId, clientId, staffId, status, emailAddress, emailSubject, message, emailAttachmentFileFormat,
                stretchyReport, stretchyReportParamMap, emailAttachmentFileFormatOptions, stretchyReportParamDateOptions, campaignName,
                sentDate, errorMessage);
    }
    private EmailData(final Long id, final Long groupId, final Long clientId, final Long staffId, final EnumOptionData status,
            final String emailAddress, final String emailSubject, final String message, final EnumOptionData emailAttachmentFileFormat,
            final ReportData stretchyReport, final String stretchyReportParamMap,
            final List<EnumOptionData> emailAttachmentFileFormatOptions, final List<EnumOptionData> stretchyReportParamDateOptions,
            final String campaignName, final LocalDate sentDate, final String errorMessage) {
        this.id = id;
        this.groupId = groupId;
        this.clientId = clientId;
        this.staffId = staffId;
        this.status = status;
        this.emailAddress = emailAddress;
        this.emailSubject = emailSubject;
        this.emailMessage = message;
        this.emailAttachmentFileFormat = emailAttachmentFileFormat;
        this.stretchyReport = stretchyReport;
        this.stretchyReportParamMap = stretchyReportParamMap;
        this.emailAttachmentFileFormatOptions = emailAttachmentFileFormatOptions;
        this.stretchyReportParamDateOptions = stretchyReportParamDateOptions;
        this.campaignName = campaignName;
        this.sentDate = sentDate;
        this.errorMessage = errorMessage;
    }
    public Long getId() {
        return id;
    }
    public Long getGroupId() {
        return groupId;
    }
    public Long getClientId() {
        return clientId;
    }
    public Long getStaffId() {
        return staffId;
    }
    public EnumOptionData getStatus() {
        return status;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getMessage() {
        return emailMessage;
    }
    public String getCampaignName() {
        return this.campaignName;
    }
    public LocalDate getSentDate() {
        return this.sentDate;
    }
    public String getEmailSubject() {
        return emailSubject;
    }
    public EnumOptionData getEmailAttachmentFileFormat() {
        return emailAttachmentFileFormat;
    }
    public ReportData getStretchyReport() {
        return stretchyReport;
    }
    public String getStretchyReportParamMap() {
        return stretchyReportParamMap;
    }
    public List<EnumOptionData> getEmailAttachmentFileFormatOptions() {
        return emailAttachmentFileFormatOptions;
    }
    public List<EnumOptionData> getStretchyReportParamDateOptions() {
        return stretchyReportParamDateOptions;
    }
}
