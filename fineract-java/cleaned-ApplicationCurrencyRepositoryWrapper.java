
package org.apache.fineract.organisation.monetary.domain;
import org.apache.fineract.organisation.monetary.exception.CurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ApplicationCurrencyRepositoryWrapper {
    private final ApplicationCurrencyRepository repository;
    @Autowired
    public ApplicationCurrencyRepositoryWrapper(final ApplicationCurrencyRepository repository) {
        this.repository = repository;
    }
    public ApplicationCurrency findOneWithNotFoundDetection(final MonetaryCurrency currency) {
        final ApplicationCurrency defaultApplicationCurrency = this.repository.findOneByCode(currency.getCode());
        if (defaultApplicationCurrency == null) {
            throw new CurrencyNotFoundException(currency.getCode());
        }
        final ApplicationCurrency applicationCurrency = ApplicationCurrency.from(defaultApplicationCurrency,
                currency.getDigitsAfterDecimal(), currency.getCurrencyInMultiplesOf());
        return applicationCurrency;
    }
    public ApplicationCurrency findOneWithNotFoundDetection(final String currencyCode) {
        final ApplicationCurrency applicationCurrency = this.repository.findOneByCode(currencyCode);
        if (applicationCurrency == null) {
            throw new CurrencyNotFoundException(currencyCode);
        }
        return applicationCurrency;
    }
}
