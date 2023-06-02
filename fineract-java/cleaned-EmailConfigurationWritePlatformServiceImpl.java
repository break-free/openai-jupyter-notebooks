
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailConfigurationValidator;
import org.apache.fineract.infrastructure.campaigns.email.domain.EmailConfiguration;
import org.apache.fineract.infrastructure.campaigns.email.domain.EmailConfigurationRepository;
import org.apache.fineract.infrastructure.campaigns.email.exception.EmailConfigurationSMTPUsernameNotValid;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmailConfigurationWritePlatformServiceImpl implements EmailConfigurationWritePlatformService {
    private final PlatformSecurityContext context;
    private final EmailConfigurationRepository repository;
    private final EmailConfigurationValidator emailConfigurationValidator;
    @Autowired
    public EmailConfigurationWritePlatformServiceImpl(final PlatformSecurityContext context, final EmailConfigurationRepository repository,
            final EmailConfigurationValidator emailConfigurationValidator) {
        this.context = context;
        this.repository = repository;
        this.emailConfigurationValidator = emailConfigurationValidator;
    }
    @Override
    public CommandProcessingResult update(final JsonCommand command) {
        final AppUser currentUser = this.context.authenticatedUser();
        this.emailConfigurationValidator.validateUpdateConfiguration(command.json());
        final String smtpUsername = command.stringValueOfParameterNamed("SMTP_USERNAME");
        if (!this.emailConfigurationValidator.isValidEmail(smtpUsername)) {
            throw new EmailConfigurationSMTPUsernameNotValid(smtpUsername);
        }
        final Map<String, Object> changes = new HashMap<>(4);
        Collection<EmailConfiguration> configurations = this.repository.findAll();
        for (EmailConfiguration config : configurations) {
            if (config.getName() != null) {
                String value = command.stringValueOfParameterNamed(config.getName());
                config.setValue(value);
                changes.put(config.getName(), value);
                this.repository.saveAndFlush(config);
            }
        }
        return new CommandProcessingResultBuilder() 
                .with(changes).build();
    }
}
