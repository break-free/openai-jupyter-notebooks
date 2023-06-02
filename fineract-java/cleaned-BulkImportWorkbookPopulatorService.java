
package org.apache.fineract.infrastructure.bulkimport.service;
import javax.ws.rs.core.Response;
public interface BulkImportWorkbookPopulatorService {
    Response getTemplate(String entityType, Long officeId, Long staffId, String dateFormat);
}
