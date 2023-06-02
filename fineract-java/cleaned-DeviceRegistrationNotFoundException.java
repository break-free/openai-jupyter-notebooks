
package org.apache.fineract.infrastructure.gcm.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class DeviceRegistrationNotFoundException extends AbstractPlatformResourceNotFoundException {
    public DeviceRegistrationNotFoundException(final Long id) {
        super("error.msg.device.registration.id.invalid", "Device registration with identifier " + id + " does not exist", id);
    }
    public DeviceRegistrationNotFoundException(final Long clientId, String value) {
        super("error.msg.device.registration." + value + ".invalid",
                "Device registration with " + value + " identifier " + clientId + " does not exist", clientId);
    }
    public DeviceRegistrationNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.device.registration.id.invalid", "Device registration with identifier " + id + " does not exist", id, e);
    }
    public DeviceRegistrationNotFoundException(Long clientId, String value, EmptyResultDataAccessException e) {
        super("error.msg.device.registration." + value + ".invalid",
                "Device registration with " + value + " identifier " + clientId + " does not exist", clientId, e);
    }
}
