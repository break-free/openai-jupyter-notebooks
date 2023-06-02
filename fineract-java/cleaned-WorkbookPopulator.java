
package org.apache.fineract.infrastructure.bulkimport.populator;
import org.apache.poi.ss.usermodel.Workbook;
public interface WorkbookPopulator {
    void populate(Workbook workbook, String dateFormat);
}
