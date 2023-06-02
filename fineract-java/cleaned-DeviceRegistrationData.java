
package org.apache.fineract.infrastructure.gcm.domain;
import java.time.LocalDateTime;
import org.apache.fineract.portfolio.client.data.ClientData;
public final class DeviceRegistrationData {
    public Long id;
    public ClientData clientData;
    public String registrationId;
    public LocalDateTime updatedOnDate;
    private DeviceRegistrationData(final Long id, final ClientData clientData, final String registrationId,
            final LocalDateTime updatedOnDate) {
        this.id = id;
        this.clientData = clientData;
        this.registrationId = registrationId;
        this.updatedOnDate = updatedOnDate;
    }
    public static DeviceRegistrationData instance(final Long id, final ClientData clientData, final String registrationId,
            final LocalDateTime updatedOnDate) {
        return new DeviceRegistrationData(id, clientData, registrationId, updatedOnDate);
    }
}
