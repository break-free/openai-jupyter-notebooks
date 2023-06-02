
package org.apache.fineract.organisation.monetary.service;
import org.apache.fineract.organisation.monetary.data.ApplicationCurrencyConfigurationData;
public interface OrganisationCurrencyReadPlatformService {
    ApplicationCurrencyConfigurationData retrieveCurrencyConfiguration();
}
