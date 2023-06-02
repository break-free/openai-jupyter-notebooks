
package org.apache.fineract.infrastructure.campaigns.email.service;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailMessageWithAttachmentData;
public interface EmailMessageJobEmailService {
    void sendEmailWithAttachment(EmailMessageWithAttachmentData emailMessageWithAttachmentData);
}
