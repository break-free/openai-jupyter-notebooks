
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailMessageWithAttachmentData;
import org.apache.fineract.infrastructure.configuration.data.SMTPCredentialsData;
import org.apache.fineract.infrastructure.configuration.service.ExternalServicesPropertiesReadPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public final class EmailMessageJobEmailServiceImpl implements EmailMessageJobEmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailMessageJobEmailServiceImpl.class);
    private final ExternalServicesPropertiesReadPlatformService externalServicesReadPlatformService;
    @Autowired
    public EmailMessageJobEmailServiceImpl(ExternalServicesPropertiesReadPlatformService externalServicesReadPlatformService) {
        this.externalServicesReadPlatformService = externalServicesReadPlatformService;
    }
    @Override
    public void sendEmailWithAttachment(EmailMessageWithAttachmentData emailMessageWithAttachmentData) {
        final SMTPCredentialsData smtpCredentialsData = this.externalServicesReadPlatformService.getSMTPCredentials();
        try {
            JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
            javaMailSenderImpl.setHost(smtpCredentialsData.getHost());
            javaMailSenderImpl.setPort(Integer.parseInt(smtpCredentialsData.getPort()));
            javaMailSenderImpl.setUsername(smtpCredentialsData.getUsername());
            javaMailSenderImpl.setPassword(smtpCredentialsData.getPassword());
            javaMailSenderImpl
                    .setJavaMailProperties(this.getJavaMailProperties(smtpCredentialsData, javaMailSenderImpl.getJavaMailProperties()));
            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(smtpCredentialsData.getFromEmail());
            mimeMessageHelper.setTo(emailMessageWithAttachmentData.getTo());
            mimeMessageHelper.setText(emailMessageWithAttachmentData.getText(), true);
            mimeMessageHelper.setSubject(emailMessageWithAttachmentData.getSubject());
            final List<File> attachments = emailMessageWithAttachmentData.getAttachments();
            if (attachments != null && attachments.size() > 0) {
                for (final File attachment : attachments) {
                    if (attachment != null) {
                        mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                    }
                }
            }
            javaMailSenderImpl.send(mimeMessage);
        } catch (MessagingException e) {
            LOG.error("Could not send emai Problem occurred in sendEmailWithAttachment function", e);
        }
    }
    private Properties getJavaMailProperties(SMTPCredentialsData smtpCredentialsData, Properties properties) {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", smtpCredentialsData.getHost());
        if (smtpCredentialsData.isUseTLS()) {
            if (smtpCredentialsData.getPort().equals("465")) {
                properties.put("mail.smtp.starttls.enable", "false");
            }
        }
        return properties;
    }
}
