
package org.apache.fineract.portfolio.client.domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientTransferDetailsRepositoryWrapper {
    private final ClientTransferDetailsRepository repository;
    @Autowired
    public ClientTransferDetailsRepositoryWrapper(final ClientTransferDetailsRepository repository) {
        this.repository = repository;
    }
    public void save(final ClientTransferDetails clientTransferDetails) {
        this.repository.save(clientTransferDetails);
    }
}
