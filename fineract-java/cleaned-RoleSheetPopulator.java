
package org.apache.fineract.infrastructure.bulkimport.populator;
import java.util.List;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.fineract.useradministration.data.RoleData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class RoleSheetPopulator extends AbstractWorkbookPopulator {
    private List<RoleData> rolesList;
    private static final int ID_COL = 0;
    private static final int ROLE_NAME = 1;
    public RoleSheetPopulator(List<RoleData> roles) {
        this.rolesList = roles;
    }
    @Override
    public void populate(Workbook workbook, String dateFormat) {
        int rowIndex = 1;
        Sheet rolesSheet = workbook.createSheet(TemplatePopulateImportConstants.ROLES_SHEET_NAME);
        setLayout(rolesSheet);
        populateRoles(rolesSheet, rowIndex);
        rolesSheet.protectSheet("");
    }
    private void populateRoles(Sheet rolesSheet, int rowIndex) {
        for (RoleData role : rolesList) {
            Row row = rolesSheet.createRow(rowIndex);
            writeLong(ID_COL, row, role.getId());
            writeString(ROLE_NAME, row, role.getName().trim().replaceAll("[ )(]", "_"));
            rowIndex++;
        }
    }
    private void setLayout(Sheet rolesSheet) {
        rolesSheet.setColumnWidth(ID_COL, TemplatePopulateImportConstants.SMALL_COL_SIZE);
        rolesSheet.setColumnWidth(ROLE_NAME, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        Row rowHeader = rolesSheet.createRow(TemplatePopulateImportConstants.ROWHEADER_INDEX);
        rowHeader.setHeight(TemplatePopulateImportConstants.ROW_HEADER_HEIGHT);
        writeString(ID_COL, rowHeader, "ID");
        writeString(ROLE_NAME, rowHeader, "Role Name");
    }
}
