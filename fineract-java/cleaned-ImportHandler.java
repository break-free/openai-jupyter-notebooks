
package org.apache.fineract.infrastructure.bulkimport.importhandler;
import org.apache.fineract.infrastructure.bulkimport.data.Count;
import org.apache.poi.ss.usermodel.Workbook;
public interface ImportHandler {
    Count process(Workbook workbook, String locale, String dateFormat);
}
