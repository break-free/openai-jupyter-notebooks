
package org.apache.fineract.infrastructure.documentmanagement.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_image")
public final class Image extends AbstractPersistableCustom {
    @Column(name = "location", length = 500)
    private String location;
    @Column(name = "storage_type_enum")
    private Integer storageType;
    public Image(final String location, final StorageType storageType) {
        this.location = location;
        this.storageType = storageType.getValue();
    }
    Image() {
    }
    public String getLocation() {
        return this.location;
    }
    public Integer getStorageType() {
        return this.storageType;
    }
    public void setLocation(final String location) {
        this.location = location;
    }
    public void setStorageType(final Integer storageType) {
        this.storageType = storageType;
    }
}
