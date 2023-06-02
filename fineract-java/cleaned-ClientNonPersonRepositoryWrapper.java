
package org.apache.fineract.portfolio.client.domain;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.exception.ClientNonPersonNotFoundByClientIdException;
import org.apache.fineract.portfolio.client.exception.ClientNonPersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientNonPersonRepositoryWrapper {
    private final ClientNonPersonRepository repository;
    private final PlatformSecurityContext context;
    @Autowired
    public ClientNonPersonRepositoryWrapper(final ClientNonPersonRepository repository, final PlatformSecurityContext context) {
        this.repository = repository;
        this.context = context;
    }
    public ClientNonPerson findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ClientNonPersonNotFoundException(id));
    }
    public ClientNonPerson findOneByClientId(final Long clientId) {
        return this.repository.findByClientId(clientId);
    }
    public ClientNonPerson findOneByClientIdWithNotFoundDetection(final Long clientId) {
        final ClientNonPerson clientNonPerson = this.repository.findByClientId(clientId);
        if (clientNonPerson == null) {
            throw new ClientNonPersonNotFoundByClientIdException(clientId);
        }
        return clientNonPerson;
    }
    public void save(final ClientNonPerson clientNonPerson) {
        this.repository.save(clientNonPerson);
    }
    public void saveAndFlush(final ClientNonPerson clientNonPerson) {
        this.repository.saveAndFlush(clientNonPerson);
    }
    public void delete(final ClientNonPerson clientNonPerson) {
        this.repository.delete(clientNonPerson);
    }
}
