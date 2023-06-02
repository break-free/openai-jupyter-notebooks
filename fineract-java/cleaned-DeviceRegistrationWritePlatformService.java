
package org.apache.fineract.infrastructure.gcm.service;
import org.apache.fineract.infrastructure.gcm.domain.DeviceRegistration;
public interface DeviceRegistrationWritePlatformService {
    DeviceRegistration registerDevice(Long clientId, String registrationId);
    DeviceRegistration updateDeviceRegistration(Long id, Long clientId, String registrationId);
    void deleteDeviceRegistration(Long id);
}
