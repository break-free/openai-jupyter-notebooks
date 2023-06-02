
package org.apache.fineract.infrastructure.reportmailingjob.service;
import java.util.Collection;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.reportmailingjob.ReportMailingJobConstants;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobConfigurationData;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobEmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class ReportMailingJobEmailServiceImpl implements ReportMailingJobEmailService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportMailingJobEmailServiceImpl.class);
    private final ReportMailingJobConfigurationReadPlatformService reportMailingJobConfigurationReadPlatformService;
    private Collection<ReportMailingJobConfigurationData> reportMailingJobConfigurationDataCollection;
    @Autowired
    public ReportMailingJobEmailServiceImpl(
            final ReportMailingJobConfigurationReadPlatformService reportMailingJobConfigurationReadPlatformService) {
        this.reportMailingJobConfigurationReadPlatformService = reportMailingJobConfigurationReadPlatformService;
    }
    @Override
    public void sendEmailWithAttachment(ReportMailingJobEmailData reportMailingJobEmailData) {
        try {
            this.reportMailingJobConfigurationDataCollection = this.reportMailingJobConfigurationReadPlatformService
                    .retrieveAllReportMailingJobConfigurations();
            JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
            javaMailSenderImpl.setHost(this.getGmailSmtpServer());
            javaMailSenderImpl.setPort(this.getGmailSmtpPort());
            javaMailSenderImpl.setUsername(this.getGmailSmtpUsername());
            javaMailSenderImpl.setPassword(this.getGmailSmtpPassword());
            javaMailSenderImpl.setJavaMailProperties(this.getJavaMailProperties());
            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(reportMailingJobEmailData.getTo());
            mimeMessageHelper.setText(reportMailingJobEmailData.getText());
            mimeMessageHelper.setSubject(reportMailingJobEmailData.getSubject());
            if (reportMailingJobEmailData.getAttachment() != null) {
                mimeMessageHelper.addAttachment(reportMailingJobEmailData.getAttachment().getName(),
                        reportMailingJobEmailData.getAttachment());
            }
            javaMailSenderImpl.send(mimeMessage);
        }
        catch (MessagingException e) {
            LOG.error("Problem occurred in sendEmailWithAttachment function", e);
        }
    }
    private Properties getJavaMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", this.getGmailSmtpServer());
        return properties;
    }
    private ReportMailingJobConfigurationData getReportMailingJobConfigurationData(final String name) {
        ReportMailingJobConfigurationData reportMailingJobConfigurationData = null;
        if (this.reportMailingJobConfigurationDataCollection != null && !this.reportMailingJobConfigurationDataCollection.isEmpty()) {
            for (ReportMailingJobConfigurationData reportMailingJobConfigurationDataObject : this.reportMailingJobConfigurationDataCollection) {
                String configurationName = reportMailingJobConfigurationDataObject.getName();
                if (!StringUtils.isEmpty(configurationName) && configurationName.equals(name)) {
                    reportMailingJobConfigurationData = reportMailingJobConfigurationDataObject;
                    break;
                }
            }
        }
        return reportMailingJobConfigurationData;
    }
    private String getGmailSmtpServer() {
        final ReportMailingJobConfigurationData reportMailingJobConfigurationData = this
                .getReportMailingJobConfigurationData(ReportMailingJobConstants.GMAIL_SMTP_SERVER);
        return (reportMailingJobConfigurationData != null) ? reportMailingJobConfigurationData.getValue() : null;
    }
    private Integer getGmailSmtpPort() {
        final ReportMailingJobConfigurationData reportMailingJobConfigurationData = this
                .getReportMailingJobConfigurationData(ReportMailingJobConstants.GMAIL_SMTP_PORT);
        final String portNumber = (reportMailingJobConfigurationData != null) ? reportMailingJobConfigurationData.getValue() : null;
        return (portNumber != null) ? Integer.parseInt(portNumber) : null;
    }
    private String getGmailSmtpUsername() {
        final ReportMailingJobConfigurationData reportMailingJobConfigurationData = this
                .getReportMailingJobConfigurationData(ReportMailingJobConstants.GMAIL_SMTP_USERNAME);
        return (reportMailingJobConfigurationData != null) ? reportMailingJobConfigurationData.getValue() : null;
    }
    private String getGmailSmtpPassword() {
        final ReportMailingJobConfigurationData reportMailingJobConfigurationData = this
                .getReportMailingJobConfigurationData(ReportMailingJobConstants.GMAIL_SMTP_PASSWORD);
        return (reportMailingJobConfigurationData != null) ? reportMailingJobConfigurationData.getValue() : null;
    }
}
