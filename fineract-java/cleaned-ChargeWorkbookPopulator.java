
package org.apache.fineract.infrastructure.bulkimport.populator.charge;
import org.apache.fineract.infrastructure.bulkimport.constants.ChargeConstants;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.fineract.infrastructure.bulkimport.populator.AbstractWorkbookPopulator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class ChargeWorkbookPopulator extends AbstractWorkbookPopulator {
    public ChargeWorkbookPopulator() {
    }
    @Override
    public void populate(Workbook workbook, String dateFormat) {
        Sheet chargeSheet = workbook.createSheet(TemplatePopulateImportConstants.CHARGE_SHEET_NAME);
        setLayout(chargeSheet);
    }
    private void setLayout(final Sheet worksheet) {
        Row rowHeader = worksheet.createRow(TemplatePopulateImportConstants.ROWHEADER_INDEX);
        worksheet.setColumnWidth(0, TemplatePopulateImportConstants.SMALL_COL_SIZE);
        worksheet.setColumnWidth(ChargeConstants.CHARGE_NAME_COL, TemplatePopulateImportConstants.SMALL_COL_SIZE);
        worksheet.setColumnWidth(ChargeConstants.CHARGE_AMOUNT_COL, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        worksheet.setColumnWidth(ChargeConstants.CHARGE_CALCULATION_TYPE_COL, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        worksheet.setColumnWidth(ChargeConstants.CHARGE_TIME_TYPE_COL, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        writeString(0, rowHeader, "ID");
        writeString(ChargeConstants.CHARGE_NAME_COL, rowHeader, "Charge Name*");
        writeString(ChargeConstants.CHARGE_AMOUNT_COL, rowHeader, "Charge Amount*");
        writeString(ChargeConstants.CHARGE_CALCULATION_TYPE_COL, rowHeader, "Charge Calculation Type*");
        writeString(ChargeConstants.CHARGE_TIME_TYPE_COL, rowHeader, "Charge Time Type*");
    }
    @SuppressWarnings("unused")
    private void setRules(Sheet workSheet, final String dateFormat) {
    }
}
