
package org.apache.fineract.infrastructure.documentmanagement.contentrepository;
import java.io.InputStream;
import org.apache.fineract.infrastructure.core.domain.Base64EncodedImage;
import org.apache.fineract.infrastructure.documentmanagement.command.DocumentCommand;
import org.apache.fineract.infrastructure.documentmanagement.data.DocumentData;
import org.apache.fineract.infrastructure.documentmanagement.data.FileData;
import org.apache.fineract.infrastructure.documentmanagement.data.ImageData;
import org.apache.fineract.infrastructure.documentmanagement.domain.StorageType;
public interface ContentRepository {
    Integer MAX_FILE_UPLOAD_SIZE_IN_MB = 5;
    Integer MAX_IMAGE_UPLOAD_SIZE_IN_MB = 1;
    String saveFile(InputStream uploadedInputStream, DocumentCommand documentCommand);
    void deleteFile(String documentPath);
    FileData fetchFile(DocumentData documentData);
    String saveImage(InputStream uploadedInputStream, Long resourceId, String imageName, Long fileSize);
    String saveImage(Base64EncodedImage base64EncodedImage, Long resourceId, String imageName);
    void deleteImage(String location);
    FileData fetchImage(ImageData imageData);
    StorageType getStorageType();
}
