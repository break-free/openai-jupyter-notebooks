
package org.apache.fineract.portfolio.client.domain;
import org.apache.fineract.organisation.office.domain.OrganisationCurrencyRepositoryWrapper;
import org.apache.fineract.portfolio.client.exception.ClientTransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientTransactionRepositoryWrapper {
    private final ClientTransactionRepository repository;
    private final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepository;
    @Autowired
    public ClientTransactionRepositoryWrapper(final ClientTransactionRepository repository,
            final OrganisationCurrencyRepositoryWrapper currencyRepositoryWrapper) {
        this.repository = repository;
        this.organisationCurrencyRepository = currencyRepositoryWrapper;
    }
    public ClientTransaction findOneWithNotFoundDetection(final Long clientId, final Long transactionId) {
        final ClientTransaction clientTransaction = this.repository.findById(transactionId)
                .orElseThrow(() -> new ClientTransactionNotFoundException(clientId, transactionId));
        if (!clientTransaction.getClientId().equals(clientId)) {
            throw new ClientTransactionNotFoundException(clientId, transactionId);
        }
        clientTransaction.setCurrency(organisationCurrencyRepository.findOneWithNotFoundDetection(clientTransaction.getCurrencyCode()));
        return clientTransaction;
    }
    public void save(final ClientTransaction clientTransaction) {
        this.repository.save(clientTransaction);
    }
    public void saveAndFlush(final ClientTransaction clientTransaction) {
        this.repository.saveAndFlush(clientTransaction);
    }
    public void delete(final ClientTransaction clientTransaction) {
        this.repository.delete(clientTransaction);
    }
}
