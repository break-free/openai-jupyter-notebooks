
package org.apache.fineract.infrastructure.bulkimport.data;
import org.apache.fineract.infrastructure.core.domain.FineractContext;
import org.apache.fineract.infrastructure.core.domain.FineractEvent;
import org.apache.poi.ss.usermodel.Workbook;
public final class BulkImportEvent extends FineractEvent {
    private final Workbook workbook;
    private final Long importId;
    private final String locale;
    private final String dateFormat;
    private BulkImportEvent(final Object source, final Workbook workbook, final Long importId, final String locale, final String dateFormat,
            FineractContext context) {
        super(source, context);
        this.workbook = workbook;
        this.importId = importId;
        this.locale = locale;
        this.dateFormat = dateFormat;
    }
    public static BulkImportEvent instance(final Object source, final Workbook workbook, final Long importId, final String locale,
            final String dateFormat, FineractContext context) {
        return new BulkImportEvent(source, workbook, importId, locale, dateFormat, context);
    }
    public Workbook getWorkbook() {
        return workbook;
    }
    public Long getImportId() {
        return importId;
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public String getLocale() {
        return locale;
    }
}
