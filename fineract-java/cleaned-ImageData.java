
package org.apache.fineract.infrastructure.documentmanagement.data;
import org.apache.fineract.infrastructure.documentmanagement.contentrepository.ContentRepositoryUtils;
import org.apache.fineract.infrastructure.documentmanagement.contentrepository.ContentRepositoryUtils.ImageMIMEtype;
import org.apache.fineract.infrastructure.documentmanagement.domain.StorageType;
public class ImageData {
    private final String location;
    private final StorageType storageType;
    private final String entityDisplayName;
    private final ContentRepositoryUtils.ImageMIMEtype contentType;
    public ImageData(final String location, final StorageType storageType, final String entityDisplayName) {
        this.location = location;
        this.storageType = storageType;
        this.entityDisplayName = entityDisplayName;
        this.contentType = ContentRepositoryUtils.ImageMIMEtype
                .fromFileExtension(ContentRepositoryUtils.imageExtensionFromFileName(location));
    }
    public ImageMIMEtype contentType() {
        return this.contentType;
    }
    public StorageType storageType() {
        return this.storageType;
    }
    public String location() {
        return this.location;
    }
    public String getEntityDisplayName() {
        return this.entityDisplayName;
    }
}
