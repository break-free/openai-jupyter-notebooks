
package org.apache.fineract.infrastructure.bulkimport.populator;
import java.util.List;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class GlAccountSheetPopulator extends AbstractWorkbookPopulator {
    private List<GLAccountData> allGlAccounts;
    private static final int ID_COL = 0;
    private static final int ACCOUNT_NAME_COL = 1;
    public GlAccountSheetPopulator(List<GLAccountData> glAccounts) {
        this.allGlAccounts = glAccounts;
    }
    @Override
    public void populate(Workbook workbook, String dateFormat) {
        int rowIndex = 1;
        Sheet glAccountSheet = workbook.createSheet(TemplatePopulateImportConstants.GL_ACCOUNTS_SHEET_NAME);
        setLayout(glAccountSheet);
        populateglAccounts(glAccountSheet, rowIndex);
        glAccountSheet.protectSheet("");
    }
    private void setLayout(Sheet worksheet) {
        worksheet.setColumnWidth(ID_COL, TemplatePopulateImportConstants.SMALL_COL_SIZE);
        worksheet.setColumnWidth(ACCOUNT_NAME_COL, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        Row rowHeader = worksheet.createRow(TemplatePopulateImportConstants.ROWHEADER_INDEX);
        rowHeader.setHeight(TemplatePopulateImportConstants.ROW_HEADER_HEIGHT);
        writeString(ID_COL, rowHeader, "Gl Account ID");
        writeString(ACCOUNT_NAME_COL, rowHeader, "Gl Account Name");
    }
    private void populateglAccounts(Sheet GlAccountSheet, int rowIndex) {
        for (GLAccountData glAccount : allGlAccounts) {
            Row row = GlAccountSheet.createRow(rowIndex);
            writeLong(ID_COL, row, glAccount.getId());
            writeString(ACCOUNT_NAME_COL, row, glAccount.getName().trim().replaceAll("[ )(]", "_"));
            rowIndex++;
        }
    }
    public Integer getGlAccountNamesSize() {
        return allGlAccounts.size();
    }
}
