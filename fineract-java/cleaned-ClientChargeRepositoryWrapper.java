
package org.apache.fineract.portfolio.client.domain;
import org.apache.fineract.organisation.office.domain.OrganisationCurrencyRepositoryWrapper;
import org.apache.fineract.portfolio.charge.exception.ChargeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientChargeRepositoryWrapper {
    private final ClientChargeRepository repository;
    private final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepository;
    @Autowired
    public ClientChargeRepositoryWrapper(final ClientChargeRepository repository,
            final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepositoryWrapper) {
        this.repository = repository;
        this.organisationCurrencyRepository = organisationCurrencyRepositoryWrapper;
    }
    public ClientCharge findOneWithNotFoundDetection(final Long id) {
        final ClientCharge clientCharge = this.repository.findById(id).orElseThrow(() -> new ChargeNotFoundException(id));
        clientCharge.setCurrency(organisationCurrencyRepository.findOneWithNotFoundDetection(clientCharge.getCharge().getCurrencyCode()));
        return clientCharge;
    }
    public void save(final ClientCharge clientCharge) {
        this.repository.save(clientCharge);
    }
    public void saveAndFlush(final ClientCharge clientCharge) {
        this.repository.saveAndFlush(clientCharge);
    }
    public void delete(final ClientCharge clientCharge) {
        this.repository.delete(clientCharge);
        this.repository.flush();
    }
}
