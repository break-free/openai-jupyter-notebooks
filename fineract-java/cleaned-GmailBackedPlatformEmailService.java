
package org.apache.fineract.infrastructure.core.service;
import java.util.Properties;
import org.apache.fineract.infrastructure.configuration.data.SMTPCredentialsData;
import org.apache.fineract.infrastructure.configuration.service.ExternalServicesPropertiesReadPlatformService;
import org.apache.fineract.infrastructure.core.domain.EmailDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
@Service
public class GmailBackedPlatformEmailService implements PlatformEmailService {
    private final ExternalServicesPropertiesReadPlatformService externalServicesReadPlatformService;
    @Autowired
    public GmailBackedPlatformEmailService(final ExternalServicesPropertiesReadPlatformService externalServicesReadPlatformService) {
        this.externalServicesReadPlatformService = externalServicesReadPlatformService;
    }
    @Override
    public void sendToUserAccount(String organisationName, String contactName, String address, String username, String unencodedPassword) {
        final String subject = "Welcome " + contactName + " to " + organisationName;
        final String body = "You are receiving this email as your email account: " + address
                + " has being used to create a user account for an organisation named [" + organisationName + "] on Mifos.\n"
                + "You can login using the following credentials:\nusername: " + username + "\n" + "password: " + unencodedPassword + "\n"
                + "You must change this password upon first log in using Uppercase, Lowercase, number and character.\n"
                + "Thank you and welcome to the organisation.";
        final EmailDetail emailDetail = new EmailDetail(subject, body, address, contactName);
        sendDefinedEmail(emailDetail);
    }
    @Override
    public void sendDefinedEmail(EmailDetail emailDetails) {
        final SMTPCredentialsData smtpCredentialsData = this.externalServicesReadPlatformService.getSMTPCredentials();
        final String authuser = smtpCredentialsData.getUsername();
        final String authpwd = smtpCredentialsData.getPassword();
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpCredentialsData.getHost()); 
        mailSender.setPort(Integer.parseInt(smtpCredentialsData.getPort())); 
        mailSender.setUsername(authuser); 
        mailSender.setPassword(authpwd); 
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", Integer.parseInt(smtpCredentialsData.getPort()));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(smtpCredentialsData.getFromEmail()); 
            message.setTo(emailDetails.getAddress());
            message.setSubject(emailDetails.getSubject());
            message.setText(emailDetails.getBody());
            mailSender.send(message);
        } catch (Exception e) {
            throw new PlatformEmailSendException(e);
        }
    }
}
