
package org.apache.fineract.infrastructure.documentmanagement.service;
import org.apache.fineract.infrastructure.documentmanagement.data.FileData;
public interface ImageReadPlatformService {
    FileData retrieveImage(String entityType, Long entityId);
}
