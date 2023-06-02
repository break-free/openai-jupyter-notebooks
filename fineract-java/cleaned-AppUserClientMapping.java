
package org.apache.fineract.useradministration.domain;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;
@Entity
@Table(name = "m_selfservice_user_client_mapping")
public class AppUserClientMapping extends AbstractPersistableCustom {
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "appuser_id", nullable = false)
    private AppUser appUser;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    public AppUserClientMapping() {
    }
    public AppUserClientMapping(AppUser appUser, Client client) {
        this.appUser = appUser;
        this.client = client;
    }
    public Client getClient() {
        return this.client;
    }
    public AppUser getAppUser() {
        return appUser;
    }
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppUserClientMapping)) {
            return false;
        }
        AppUserClientMapping that = (AppUserClientMapping) obj;
        return null == this.client.getId() ? false : this.client.getId().equals(that.client.getId());
    }
    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == this.client ? 0 : this.client.getId().hashCode() * 31;
        return hashCode;
    }
}
