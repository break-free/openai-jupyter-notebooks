
package org.apache.fineract.organisation.office.domain;
import org.apache.fineract.organisation.monetary.exception.OrganizationalCurrencyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrganisationCurrencyRepositoryWrapper {
    private final OrganisationCurrencyRepository repository;
    @Autowired
    public OrganisationCurrencyRepositoryWrapper(final OrganisationCurrencyRepository repository) {
        this.repository = repository;
    }
    public OrganisationCurrency findOneWithNotFoundDetection(final String currencyCode) {
        final OrganisationCurrency organisationCurrency = this.repository.findOneByCode(currencyCode);
        if (organisationCurrency == null) {
            throw new OrganizationalCurrencyNotFoundException(currencyCode);
        }
        return organisationCurrency;
    }
}
