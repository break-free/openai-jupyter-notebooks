
package org.apache.fineract.infrastructure.campaigns.email.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class EmailConfigurationNotFoundException extends AbstractPlatformResourceNotFoundException {
    public EmailConfigurationNotFoundException(final String name) {
        super("error.msg.email.configuration.name.not.found", "Email configuration with name " + name + " does not exist", name);
    }
    public EmailConfigurationNotFoundException(String name, EmptyResultDataAccessException e) {
        super("error.msg.email.configuration.name.not.found", "Email configuration with name " + name + " does not exist", name, e);
    }
}
