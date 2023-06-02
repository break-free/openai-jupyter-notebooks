
package org.apache.fineract.infrastructure.documentmanagement.service;
import java.io.InputStream;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.domain.Base64EncodedImage;
import org.springframework.security.access.prepost.PreAuthorize;
public interface ImageWritePlatformService {
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'CREATE_CLIENTIMAGE','CREATE_STAFFIMAGE')")
    CommandProcessingResult saveOrUpdateImage(String entityName, Long entityId, String imageName, InputStream inputStream, Long fileSize);
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'CREATE_CLIENTIMAGE','CREATE_STAFFIMAGE')")
    CommandProcessingResult saveOrUpdateImage(String entityName, Long entityId, Base64EncodedImage encodedImage);
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'DELETE_CLIENTIMAGE','DELETE_STAFFIMAGE')")
    CommandProcessingResult deleteImage(String entityName, Long entityId);
}
