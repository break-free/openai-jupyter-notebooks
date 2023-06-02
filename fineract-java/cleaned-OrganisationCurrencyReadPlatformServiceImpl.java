
package org.apache.fineract.organisation.monetary.service;
import java.util.Collection;
import org.apache.fineract.organisation.monetary.data.ApplicationCurrencyConfigurationData;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrganisationCurrencyReadPlatformServiceImpl implements OrganisationCurrencyReadPlatformService {
    private final CurrencyReadPlatformService currencyReadPlatformService;
    @Autowired
    public OrganisationCurrencyReadPlatformServiceImpl(final CurrencyReadPlatformService currencyReadPlatformService) {
        this.currencyReadPlatformService = currencyReadPlatformService;
    }
    @Override
    public ApplicationCurrencyConfigurationData retrieveCurrencyConfiguration() {
        final Collection<CurrencyData> selectedCurrencyOptions = this.currencyReadPlatformService.retrieveAllowedCurrencies();
        final Collection<CurrencyData> currencyOptions = this.currencyReadPlatformService.retrieveAllPlatformCurrencies();
        currencyOptions.removeAll(selectedCurrencyOptions);
        return new ApplicationCurrencyConfigurationData(currencyOptions, selectedCurrencyOptions);
    }
}
