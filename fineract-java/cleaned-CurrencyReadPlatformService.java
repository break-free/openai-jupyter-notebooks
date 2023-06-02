
package org.apache.fineract.organisation.monetary.service;
import java.util.Collection;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public interface CurrencyReadPlatformService {
    Collection<CurrencyData> retrieveAllowedCurrencies();
    Collection<CurrencyData> retrieveAllPlatformCurrencies();
    CurrencyData retrieveCurrency(String code);
}
