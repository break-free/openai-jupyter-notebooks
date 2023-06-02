
package org.apache.fineract.infrastructure.reportmailingjob.data;
import java.io.File;
public class ReportMailingJobEmailData {
    private final String to;
    private final String text;
    private final String subject;
    private final File attachment;
    public ReportMailingJobEmailData(final String to, final String text, final String subject, final File attachment) {
        this.to = to;
        this.text = text;
        this.subject = subject;
        this.attachment = attachment;
    }
    public String getTo() {
        return to;
    }
    public String getText() {
        return text;
    }
    public String getSubject() {
        return subject;
    }
    public File getAttachment() {
        return attachment;
    }
}
