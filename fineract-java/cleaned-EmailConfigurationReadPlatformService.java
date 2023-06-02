
package org.apache.fineract.infrastructure.campaigns.email.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.campaigns.email.data.EmailConfigurationData;
public interface EmailConfigurationReadPlatformService {
    Collection<EmailConfigurationData> retrieveAll();
    EmailConfigurationData retrieveOne(String name);
}
