
package org.apache.fineract.infrastructure.reportmailingjob.service;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobEmailData;
public interface ReportMailingJobEmailService {
    void sendEmailWithAttachment(ReportMailingJobEmailData reportMailingJobEmailData);
}
