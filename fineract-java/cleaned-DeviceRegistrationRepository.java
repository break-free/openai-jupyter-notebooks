
package org.apache.fineract.infrastructure.gcm.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface DeviceRegistrationRepository
        extends JpaRepository<DeviceRegistration, Long>, JpaSpecificationExecutor<DeviceRegistration> {
    String FIND_DEVICE_REGISTRATION_BY_CLIENT = "select dr from DeviceRegistration dr where dr.client.id =:clientId ";
    @Query(FIND_DEVICE_REGISTRATION_BY_CLIENT)
    DeviceRegistration findDeviceRegistrationByClientId(@Param("clientId") Long clientId);
}
