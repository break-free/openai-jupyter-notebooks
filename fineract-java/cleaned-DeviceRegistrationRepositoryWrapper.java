
package org.apache.fineract.infrastructure.gcm.domain;
import org.apache.fineract.infrastructure.gcm.exception.DeviceRegistrationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DeviceRegistrationRepositoryWrapper {
    private final DeviceRegistrationRepository repository;
    @Autowired
    public DeviceRegistrationRepositoryWrapper(DeviceRegistrationRepository repository) {
        this.repository = repository;
    }
    public DeviceRegistration findOneWithNotFoundDetection(final Long deviceRegistrationId) {
        return this.repository.findById(deviceRegistrationId)
                .orElseThrow(() -> new DeviceRegistrationNotFoundException(deviceRegistrationId));
    }
    public void save(final DeviceRegistration deviceRegistration) {
        this.repository.save(deviceRegistration);
    }
    public void delete(final DeviceRegistration deviceRegistration) {
        this.repository.delete(deviceRegistration);
    }
    public void saveAndFlush(final DeviceRegistration deviceRegistration) {
        this.repository.saveAndFlush(deviceRegistration);
    }
    public DeviceRegistration findDeviceRegistrationByClientId(Long clientId) {
        return this.repository.findDeviceRegistrationByClientId(clientId);
    }
}
