
package org.apache.fineract.portfolio.client.domain;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.exception.ClientNotActiveException;
import org.apache.fineract.portfolio.client.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ClientRepositoryWrapper {
    private final ClientRepository repository;
    private final PlatformSecurityContext context;
    @Autowired
    public ClientRepositoryWrapper(final ClientRepository repository, final PlatformSecurityContext context) {
        this.repository = repository;
        this.context = context;
    }
    @Transactional(readOnly = true)
    public Client findOneWithNotFoundDetection(final Long id) {
        return this.findOneWithNotFoundDetection(id, false);
    }
    @Transactional(readOnly = true)
    public Client findOneWithNotFoundDetection(final Long clientId, final boolean loadLazyCollections) {
        final Client client = this.repository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        if (loadLazyCollections) {
            client.loadLazyCollections();
        }
        return client;
    }
    public List<Client> findAll(final Collection<Long> clientIds) {
        return this.repository.findAllById(clientIds);
    }
    public void save(final Client client) {
        this.repository.save(client);
    }
    public void saveAndFlush(final Client client) {
        this.repository.saveAndFlush(client);
    }
    public void delete(final Client client) {
        this.repository.delete(client);
    }
    public void flush() {
        this.repository.flush();
    }
    @Transactional(readOnly = true)
    public Client getActiveClientInUserScope(Long clientId) {
        final Client client = this.findOneWithNotFoundDetection(clientId);
        if (client.isNotActive()) {
            throw new ClientNotActiveException(client.getId());
        }
        this.context.validateAccessRights(client.getOffice().getHierarchy());
        return client;
    }
    public Client getClientByAccountNumber(String accountNumber) {
        Client client = this.repository.getClientByAccountNumber(accountNumber);
        if (client == null) {
            throw new ClientNotFoundException(accountNumber);
        }
        return client;
    }
}
