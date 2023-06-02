
package org.apache.fineract.portfolio.client.domain;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long>, JpaSpecificationExecutor<ClientAddress> {
    @Query("SELECT clientAddress FROM ClientAddress clientAddress WHERE clientAddress.client.id = :clientId AND clientAddress.addressType = :addressType AND clientAddress.isActive = :isActive ")
    ClientAddress findByClientIdAndAddressTypeAndIsActive(@Param("clientId") long clientId, @Param("addressType") CodeValue addressType,
            @Param("isActive") boolean isActive);
    @Query("SELECT clientAddress FROM ClientAddress clientAddress WHERE clientAddress.client.id = :clientId AND clientAddress.address.id = :addressId ")
    ClientAddress findByClientIdAndAddressId(@Param("clientId") long clientId, @Param("addressId") long addressId);
}
