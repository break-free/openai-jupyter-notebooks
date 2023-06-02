
package org.apache.fineract.infrastructure.campaigns.email.domain;
public enum ScheduledEmailAttachmentFileFormat {
    INVALID(0, "EmailAttachmentFileFormat.invalid", "invalid"), XLS(1, "EmailAttachmentFileFormat.xls", "xls"), PDF(2,
            "EmailAttachmentFileFormat.pdf", "pdf"), CSV(3, "EmailAttachmentFileFormat.csv", "csv");
    private final String code;
    private final String value;
    private final Integer id;
    ScheduledEmailAttachmentFileFormat(final Integer id, final String code, final String value) {
        this.value = value;
        this.code = code;
        this.id = id;
    }
    public static ScheduledEmailAttachmentFileFormat instance(final String value) {
        ScheduledEmailAttachmentFileFormat emailAttachmentFileFormat = INVALID;
        switch (value) {
            case "xls":
                emailAttachmentFileFormat = XLS;
            break;
            case "pdf":
                emailAttachmentFileFormat = PDF;
            break;
            case "csv":
                emailAttachmentFileFormat = CSV;
            break;
            default:
            break;
        }
        return emailAttachmentFileFormat;
    }
    public static ScheduledEmailAttachmentFileFormat instance(final Integer id) {
        ScheduledEmailAttachmentFileFormat emailAttachmentFileFormat = INVALID;
        switch (id) {
            case 1:
                emailAttachmentFileFormat = XLS;
            break;
            case 2:
                emailAttachmentFileFormat = PDF;
            break;
            case 3:
                emailAttachmentFileFormat = CSV;
            break;
            default:
            break;
        }
        return emailAttachmentFileFormat;
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
        return new Object[] { XLS.getId(), PDF.getId(), CSV.getId() };
    }
}
