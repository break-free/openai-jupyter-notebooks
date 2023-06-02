
package org.apache.fineract.infrastructure.reportmailingjob.domain;
public enum ReportMailingJobEmailAttachmentFileFormat {
    INVALID(0, "ReportMailingJobEmailAttachmentFileFormat.invalid", "invalid"), XLS(1, "ReportMailingJobEmailAttachmentFileFormat.xls",
            "xls"), PDF(2, "ReportMailingJobEmailAttachmentFileFormat.pdf",
                    "pdf"), CSV(3, "ReportMailingJobEmailAttachmentFileFormat.csv", "csv");
    private final String code;
    private final String value;
    private final Integer id;
    ReportMailingJobEmailAttachmentFileFormat(final Integer id, final String code, final String value) {
        this.value = value;
        this.code = code;
        this.id = id;
    }
    public static ReportMailingJobEmailAttachmentFileFormat instance(final String value) {
        ReportMailingJobEmailAttachmentFileFormat emailAttachmentFileFormat = INVALID;
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
    public static ReportMailingJobEmailAttachmentFileFormat instance(final Integer id) {
        ReportMailingJobEmailAttachmentFileFormat emailAttachmentFileFormat = INVALID;
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
