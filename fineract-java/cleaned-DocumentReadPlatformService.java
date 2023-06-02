
package org.apache.fineract.infrastructure.documentmanagement.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.documentmanagement.data.DocumentData;
import org.apache.fineract.infrastructure.documentmanagement.data.FileData;
public interface DocumentReadPlatformService {
    Collection<DocumentData> retrieveAllDocuments(String entityType, Long entityId);
    FileData retrieveFileData(String entityType, Long entityId, Long documentId);
    DocumentData retrieveDocument(String entityType, Long entityId, Long documentId);
}
