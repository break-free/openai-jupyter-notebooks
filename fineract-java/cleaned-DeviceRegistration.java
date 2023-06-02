
package org.apache.fineract.infrastructure.gcm.domain;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.domain.Client;
@Entity
@Table(name = "client_device_registration")
public final class DeviceRegistration extends AbstractPersistableCustom {
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;
    @Column(name = "registration_id", nullable = false, unique = true)
    private String registrationId;
    @Column(name = "updatedon_date", nullable = false)
    private LocalDateTime updatedOnDate;
    private DeviceRegistration() {}
    private DeviceRegistration(final Client client, final String registrationId) {
        this.client = client;
        this.registrationId = registrationId;
        this.updatedOnDate = DateUtils.getLocalDateTimeOfTenant();
    }
    public static DeviceRegistration instance(final Client client, final String registrationId) {
        return new DeviceRegistration(client, registrationId);
    }
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getRegistrationId() {
        return this.registrationId;
    }
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
    public LocalDateTime getUpdatedOnDate() {
        return this.updatedOnDate;
    }
    public void setUpdatedOnDate(LocalDateTime updatedOnDate) {
        this.updatedOnDate = updatedOnDate;
    }
}
