
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class EmailConfigurationSMTPUsernameNotValid extends AbstractPlatformDomainRuleException {
    public EmailConfigurationSMTPUsernameNotValid(final String smtpUsername) {
        super("error.msg.email.configuration.smtpusername.not.valid",
                "SMTP username configuration with email " + "'" + smtpUsername + "'" + " is not a valid email address ");
    }
}
