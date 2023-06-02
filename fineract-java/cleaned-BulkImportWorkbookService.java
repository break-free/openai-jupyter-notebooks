
package org.apache.fineract.infrastructure.bulkimport.service;
import java.io.InputStream;
import java.util.Collection;
import javax.ws.rs.core.Response;
import org.apache.fineract.infrastructure.bulkimport.data.GlobalEntityType;
import org.apache.fineract.infrastructure.bulkimport.data.ImportData;
import org.apache.fineract.infrastructure.documentmanagement.data.DocumentData;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
public interface BulkImportWorkbookService {
    Long importWorkbook(String entityType, InputStream inputStream, FormDataContentDisposition fileDetail, String locale,
            String dateFormat);
    Collection<ImportData> getImports(GlobalEntityType type);
    DocumentData getOutputTemplateLocation(String importDocumentId);
    Response getOutputTemplate(String importDocumentId);
}
