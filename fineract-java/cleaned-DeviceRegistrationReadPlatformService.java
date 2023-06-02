
package org.apache.fineract.infrastructure.gcm.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.gcm.domain.DeviceRegistrationData;
public interface DeviceRegistrationReadPlatformService {
    Collection<DeviceRegistrationData> retrieveAllDeviceRegiistrations();
    DeviceRegistrationData retrieveDeviceRegiistration(Long id);
    DeviceRegistrationData retrieveDeviceRegiistrationByClientId(Long clientId);
}
