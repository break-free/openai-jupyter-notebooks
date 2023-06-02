
package org.apache.fineract.infrastructure.bulkimport.populator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.fineract.organisation.office.data.OfficeData;
import org.apache.fineract.organisation.staff.data.StaffData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class PersonnelSheetPopulator extends AbstractWorkbookPopulator {
    private List<StaffData> personnel;
    private List<OfficeData> offices;
    private Map<String, List<StaffData>> officeToPersonnel;
    private Map<Integer, Integer[]> officeNameToBeginEndIndexesOfStaff;
    private static final int OFFICE_NAME_COL = 0;
    private static final int STAFF_NAME_COL = 1;
    private static final int STAFF_ID_COL = 2;
    public PersonnelSheetPopulator(List<StaffData> personnel, List<OfficeData> offices) {
        this.personnel = personnel;
        this.offices = offices;
    }
    @Override
    public void populate(Workbook workbook, String dateFormat) {
        Sheet staffSheet = workbook.createSheet(TemplatePopulateImportConstants.STAFF_SHEET_NAME);
        setLayout(staffSheet);
        setOfficeToPersonnelMap();
        populateStaffByOfficeName(staffSheet);
        staffSheet.protectSheet("");
    }
    private void populateStaffByOfficeName(Sheet staffSheet) {
        int rowIndex = 1;
        int startIndex = 1;
        int officeIndex = 0;
        officeNameToBeginEndIndexesOfStaff = new HashMap<>();
        Row row = staffSheet.createRow(rowIndex);
        for (OfficeData office : offices) {
            startIndex = rowIndex + 1;
            writeString(OFFICE_NAME_COL, row, office.name().trim().replaceAll("[ )(]", "_"));
            List<StaffData> staffList = officeToPersonnel.get(office.name().trim().replaceAll("[ )(]", "_"));
            if (staffList != null) {
                if (!staffList.isEmpty()) {
                    for (StaffData staff : staffList) {
                        writeString(STAFF_NAME_COL, row, staff.getDisplayName());
                        writeLong(STAFF_ID_COL, row, staff.getId());
                        row = staffSheet.createRow(++rowIndex);
                    }
                    officeNameToBeginEndIndexesOfStaff.put(officeIndex++, new Integer[] { startIndex, rowIndex });
                }
            } else {
                officeIndex++;
            }
        }
    }
    private void setOfficeToPersonnelMap() {
        officeToPersonnel = new HashMap<>();
        for (StaffData person : personnel) {
            add(person.getOfficeName().trim().replaceAll("[ )(]", "_"), person);
        }
    }
    private void add(String key, StaffData value) {
        List<StaffData> values = officeToPersonnel.get(key);
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        officeToPersonnel.put(key, values);
    }
    private void setLayout(Sheet worksheet) {
        for (Integer i = 0; i < 3; i++) {
            worksheet.setColumnWidth(i, TemplatePopulateImportConstants.MEDIUM_COL_SIZE);
        }
        Row rowHeader = worksheet.createRow(TemplatePopulateImportConstants.ROWHEADER_INDEX);
        rowHeader.setHeight(TemplatePopulateImportConstants.ROW_HEADER_HEIGHT);
        writeString(OFFICE_NAME_COL, rowHeader, "Office Name");
        writeString(STAFF_NAME_COL, rowHeader, "Staff List");
        writeString(STAFF_ID_COL, rowHeader, "Staff ID");
    }
    public Map<Integer, Integer[]> getOfficeNameToBeginEndIndexesOfStaff() {
        return officeNameToBeginEndIndexesOfStaff;
    }
}
