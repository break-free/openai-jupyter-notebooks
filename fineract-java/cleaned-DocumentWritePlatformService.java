
package org.apache.fineract.infrastructure.documentmanagement.service;
import java.io.InputStream;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.documentmanagement.command.DocumentCommand;
import org.springframework.security.access.prepost.PreAuthorize;
public interface DocumentWritePlatformService {
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'CREATE_DOCUMENT')")
    Long createDocument(DocumentCommand documentCommand, InputStream inputStream);
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'UPDATE_DOCUMENT')")
    CommandProcessingResult updateDocument(DocumentCommand documentCommand, InputStream inputStream);
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'DELETE_DOCUMENT')")
    CommandProcessingResult deleteDocument(DocumentCommand documentCommand);
    @PreAuthorize(value = "hasAnyAuthority('ALL_FUNCTIONS', 'CREATE_DOCUMENT')")
    Long createInternalDocument(String entityType, Long entityId, Long fileSize, InputStream inputStream, String mimeType, String name,
            String description, String fileName);
}
