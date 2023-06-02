
package org.apache.fineract.infrastructure.documentmanagement.data;
import org.apache.fineract.infrastructure.documentmanagement.domain.StorageType;
public class DocumentData {
    private final Long id;
    private final String parentEntityType;
    private final Long parentEntityId;
    private final String name;
    private final String fileName;
    private final Long size;
    private final String type;
    private final String location;
    private final String description;
    private final Integer storageType;
    public DocumentData(final Long id, final String parentEntityType, final Long parentEntityId, final String name, final String fileName,
            final Long size, final String type, final String description, final String location, final Integer storageType) {
        this.id = id;
        this.parentEntityType = parentEntityType;
        this.parentEntityId = parentEntityId;
        this.name = name;
        this.fileName = fileName;
        this.size = size;
        this.type = type;
        this.description = description;
        this.location = location;
        this.storageType = storageType;
    }
    public String contentType() {
        return this.type;
    }
    public String fileName() {
        return this.fileName;
    }
    public String fileLocation() {
        return this.location;
    }
    public StorageType storageType() {
        return StorageType.fromInt(this.storageType);
    }
    public String getParentEntityType() {
        return this.parentEntityType;
    }
    public Long getParentEntityId() {
        return this.parentEntityId;
    }
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public Long getSize() {
        return this.size;
    }
}
