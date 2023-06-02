
package org.apache.fineract.portfolio.client.domain;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientAddressRepositoryWrapper {
    private final ClientAddressRepository clientAddressRepository;
    @Autowired
    public ClientAddressRepositoryWrapper(final ClientAddressRepository clientAddressRepository) {
        this.clientAddressRepository = clientAddressRepository;
    }
    public ClientAddress findOneByClientIdAndAddressTypeAndIsActive(final long clientId, final CodeValue addressType,
            final boolean isActive) {
        final ClientAddress clientAddress = this.clientAddressRepository.findByClientIdAndAddressTypeAndIsActive(clientId, addressType,
                isActive);
        return clientAddress;
    }
    public ClientAddress findOneByClientIdAndAddressId(final long clientId, final long addressId) {
        final ClientAddress clientAddress = this.clientAddressRepository.findByClientIdAndAddressId(clientId, addressId);
        return clientAddress;
    }
}
